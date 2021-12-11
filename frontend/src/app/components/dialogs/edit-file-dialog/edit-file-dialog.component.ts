import { Component, Inject, OnInit } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { File } from '../../../models/file';
import {FileService} from '../../../services/file.service';
import {Folder} from '../../../models/folder';

@Component({
  selector: 'app-edit-file-dialog',
  templateUrl: './edit-file-dialog.component.html',
  styleUrls: ['./edit-file-dialog.component.scss']
})
export class EditFileDialogComponent implements OnInit {


  constructor(
    private dialogRef: MatDialogRef<EditFileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: File
  ) { }

  ngOnInit(): void {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

}
