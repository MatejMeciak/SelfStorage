import { Injectable } from '@angular/core';
import {CreateFolderDialogComponent} from "../components/dialogs/create-folder-dialog/create-folder-dialog.component";
import {Folder} from "../models/folder";
import {File} from "../models/file";
import {ContentDetailComponent} from "../components/file-components/content-detail/content-detail.component";
import {EditFileDialogComponent} from "../components/dialogs/edit-file-dialog/edit-file-dialog.component";
import {MoveToFolderDialogComponent} from "../components/dialogs/move-to-folder-dialog/move-to-folder-dialog.component";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";

@Injectable({
  providedIn: 'root'
})
export class DialogService {
  private dialogRef: MatDialogRef<ContentDetailComponent>

  constructor(private dialog: MatDialog) { }

  openFolderDialog(): void {
    const dialogRef = this.dialog.open(CreateFolderDialogComponent, { data: {name: '', access: false } as Folder });
    dialogRef.afterClosed().subscribe(folder => {
      //this.folderService.createFolder(folder).subscribe();
    });
  }

  openEditFileDialog(): void {
    const dialogRef = this.dialog.open(EditFileDialogComponent, { /*data: this.file*/ });
    dialogRef.afterClosed().subscribe(newFile => {
      //this.fileService.updateFile(newFile).subscribe();
    });
  }
  openCopyToFolderDialog(): void {
    const dialogRef = this.dialog.open(MoveToFolderDialogComponent, { /*data: {file: this.file, folder: this.folder, action: 'copy'}*/ });
    dialogRef.afterClosed().subscribe(data => {
      //this.folderService.updateFolderWithFile(data.folder.id, data.file).subscribe();
    });
  }
  openMoveToFolderDialog(): void {
    const dialogRef = this.dialog.open(MoveToFolderDialogComponent, { /*data: {file: this.file, folder: this.folder, action: 'move'}*/ });
    dialogRef.afterClosed().subscribe(data => {
      //this.folderService.updateFolderWithFile(data.folder.id, data.file.id).subscribe();
    });
  }
  shareWithDialog(): void {
    // TODO
  }

  downloadFile(): void {
    /*this.fileService.downloadFile(this.file).subscribe(blob => {
      fileSaver.saveAs(blob, this.file.name);
    });*/
  }

  openDetailDialogOf(file: File): void {
    const dialogRef = this.dialog.open(ContentDetailComponent, { data: file, panelClass: 'custom-dialog' });
    dialogRef.afterClosed().subscribe(deleteFile => {
     /* if (deleteFile) {
        this.fileService.deleteFile(file).subscribe(() =>
          this.fileService.getFiles().subscribe(files => this.files = files));
      }*/
    });
  }
  closeFileDetailDialog(deleteFile: boolean): void {
    this.dialogRef.close(deleteFile);
  }
}
