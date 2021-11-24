import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FileService} from '../../../services/file.service';
import { File} from '../../../models/file';
import {UploadFileDialogComponent} from '../dialogs/upload-file-dialog/upload-file-dialog.component';
import {FileDetailComponent} from '../file-detail/file-detail.component';
import {CreateFolderDialogComponent} from '../dialogs/create-folder-dialog/create-folder-dialog.component';
import {Folder} from '../../../models/folder';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-folder',
  templateUrl: './folder.component.html',
  styleUrls: ['./folder.component.css']
})
export class FolderComponent implements OnInit {
  folder: Folder;
  files: File[];
  constructor(private route: ActivatedRoute, private fileService: FileService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.getFiles();
    this.getFolder();
  }
  getFiles(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.fileService.getFolderFiles(id).subscribe(files => this.files = files);
  }
  getFolder(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.fileService.getFolder(id).subscribe(folder => this.folder = folder);
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).subscribe(file => this.files.push(file));
    }
  }
  openLinkDialog(): void {
    const dialogRef = this.dialog.open(UploadFileDialogComponent, { data: { fileName: '', access: false } as File });
    dialogRef.afterClosed().subscribe(file => {
      this.fileService.uploadLinkFile(file).subscribe();
    });
  }
  openDetailDialogOf(file: File): void {
    this.dialog.open(FileDetailComponent, { data: file, panelClass: 'custom-dialog' });
  }
  openFolderDialog(): void {
    const dialogRef = this.dialog.open(CreateFolderDialogComponent, { data: {folderName: '', access: false } as Folder });
    dialogRef.afterClosed().subscribe(folder => {
      this.fileService.createFolder(folder).subscribe();
    });
  }

}
