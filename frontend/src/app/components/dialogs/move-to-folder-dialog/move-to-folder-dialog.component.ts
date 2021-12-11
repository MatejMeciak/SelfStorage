import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Folder} from '../../../models/folder';
import {FolderService} from "../../../services/folder.service";

@Component({
  selector: 'app-move-to-folder-dialog',
  templateUrl: './move-to-folder-dialog.component.html',
  styleUrls: ['./move-to-folder-dialog.component.scss']
})
export class MoveToFolderDialogComponent implements OnInit {

  folders: Folder[];

  constructor(
    private dialogRef: MatDialogRef<MoveToFolderDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private folderService: FolderService) { }

  ngOnInit(): void {
    this.folderService.getFolders().subscribe(folders => this.folders = folders);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
