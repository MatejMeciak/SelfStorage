import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {File} from '../../../../models/file';
import {FileService} from '../../../../services/file.service';
import {Folder} from '../../../../models/folder';

@Component({
  selector: 'app-move-to-folder-dialog',
  templateUrl: './move-to-folder-dialog.component.html',
  styleUrls: ['./move-to-folder-dialog.component.css']
})
export class MoveToFolderDialogComponent implements OnInit {

  folders: Folder[];

  constructor(
    private dialogRef: MatDialogRef<MoveToFolderDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private fileService: FileService) { }

  ngOnInit(): void {
    this.fileService.getFolders().subscribe(folders => this.folders = folders);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
