import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subject, takeUntil } from "rxjs";

import { FileService } from '../../../services/file.service';
import { FolderService } from "../../../services/folder.service";

import { File } from '../../../models/file';
import { Folder } from '../../../models/folder';

import { UploadFileDialogComponent } from '../../dialogs/upload-file-dialog/upload-file-dialog.component';
import { CreateFolderDialogComponent } from '../../dialogs/create-folder-dialog/create-folder-dialog.component';
import { FileDetailComponent } from "../file-detail/file-detail.component";

import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.scss']
})
export class FilesComponent implements OnInit, OnDestroy {
  files: File[];
  folders$:  Observable<Folder[]>;

  unsubscribe$ = new Subject();
  constructor(private fileService: FileService, private folderService: FolderService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.fileService.getFiles().pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe(files => this.files = files);
    this.folders$ = this.folderService.getFolders();
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe(file => this.files.push(file));
    }
  }

  openFolderDialog(): void {
    const dialogRef = this.dialog.open(CreateFolderDialogComponent, { data: {name: '', access: false } as Folder });
    dialogRef.afterClosed().subscribe(folder => {
      this.folderService.createFolder(folder).subscribe();
    });
  }

  openDetailDialogOf(file: File): void {
    const dialogRef = this.dialog.open(FileDetailComponent, { data: file, panelClass: 'custom-dialog' });
    dialogRef.afterClosed().subscribe(deleteFile => {
      if (deleteFile) {
        this.fileService.deleteFile(file).subscribe(() =>
          this.fileService.getFiles().subscribe(files => this.files = files));
      }
    });
  }
  ngOnDestroy(): void {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }
}
