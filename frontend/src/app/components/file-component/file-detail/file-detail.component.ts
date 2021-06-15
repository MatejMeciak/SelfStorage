import {Component,  OnInit, Inject} from '@angular/core';
import { File } from '../../../models/file';
import { FileService } from '../../../services/file.service';
import * as fileSaver from 'file-saver';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {EditFileDialogComponent} from '../dialogs/edit-file-dialog/edit-file-dialog.component';
import { getFileUrl } from '../../../utils/utils';
import {MoveToFolderDialogComponent} from '../dialogs/move-to-folder-dialog/move-to-folder-dialog.component';
import {Folder} from '../../../models/folder';

@Component({
  selector: 'app-file-detail',
  templateUrl: './file-detail.component.html',
  styleUrls: ['./file-detail.component.css']
})
export class FileDetailComponent implements OnInit {

  folder: Folder;
  constructor(
    private fileService: FileService, private dialog: MatDialog,
    private dialogRef: MatDialogRef<FileDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public file: File
  ) { }

  get fileUrl(): string {
    return getFileUrl(this.file);
  }

  ngOnInit(): void { }

  openEditFileDialog(): void {
    const dialogRef = this.dialog.open(EditFileDialogComponent, { data: this.file });
    dialogRef.afterClosed().subscribe(newFile => {
      this.fileService.updateFile(newFile).subscribe();
    });
  }
  openCopyToFolderDialog(): void {
    const dialogRef = this.dialog.open(MoveToFolderDialogComponent, { data: {file: this.file, folder: this.folder, action: 'copy'} });
    dialogRef.afterClosed().subscribe(data => {
      this.fileService.updateFolder(data.folder.id, data.file).subscribe();
    });
  }
  openMoveToFolderDialog(): void {
    const dialogRef = this.dialog.open(MoveToFolderDialogComponent, { data: {file: this.file, folder: this.folder, action: 'move'} });
    dialogRef.afterClosed().subscribe(data => {
      this.fileService.updateFolder(data.folder.id, data.file).subscribe();
    });
  }
  shareWithDialog(): void {
    // TODO
  }

  downloadFile(): void {
    this.fileService.downloadFile(this.file).subscribe(blob => {
      fileSaver.saveAs(blob, this.file.fileName);
    });
  }
  closeDialog(deleteFile: boolean): void {
    this.dialogRef.close(deleteFile);
  }
}
