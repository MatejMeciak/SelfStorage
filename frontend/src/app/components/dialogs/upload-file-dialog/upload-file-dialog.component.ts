import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
//import {File} from '../../../../models/file';

@Component({
  selector: 'app-upload-file-dialog',
  templateUrl: './upload-file-dialog.component.html',
  styleUrls: ['./upload-file-dialog.component.scss']
})
export class UploadFileDialogComponent implements OnInit {

  constructor(
    private dialogRef: MatDialogRef<UploadFileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public file //:File
  ) { }

  ngOnInit(): void {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
