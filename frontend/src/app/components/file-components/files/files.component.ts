import { Component, OnInit } from '@angular/core';
import { File } from '../../../models/file';
import { FileService } from '../../../services/file.service';
import { Folder } from '../../../models/folder';
import {UploadFileDialogComponent} from '../dialogs/upload-file-dialog/upload-file-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {CreateFolderDialogComponent} from '../dialogs/create-folder-dialog/create-folder-dialog.component';
import {FileDetailComponent} from '../file-detail/file-detail.component';
import {FolderService} from "../../../services/folder.service";
import {LinkService} from "../../../services/link.service";

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.scss']
})
export class FilesComponent implements OnInit {

  active = 'all';

  files: File[];
  folders: Folder[];

  folderFiles: File[];
  constructor(private fileService: FileService, private folderService: FolderService, private linkService: LinkService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.fileService.getFiles().subscribe(files => this.files = files);
    this.folderService.getFolders().subscribe(folders => this.folders = folders);
  }
  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).subscribe(file => this.files.push(file));
    }
  }
  openLinkDialog(): void {
    const dialogRef = this.dialog.open(UploadFileDialogComponent, { data: { fileName: '', access: false } as File });
    dialogRef.afterClosed().subscribe(file => {
      this.linkService.uploadLink(file).subscribe();
    });
  }
  openFolderDialog(): void {
    const dialogRef = this.dialog.open(CreateFolderDialogComponent, { data: {folderName: '', access: false } as Folder });
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
}
