import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { FileDialogData } from "../../../models/FileDialogData";

@Component({
  selector: 'app-open-content-dialog',
  templateUrl: './open-content-dialog.component.html',
  styleUrls: ['./open-content-dialog.component.scss']
})
export class OpenContentDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<OpenContentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: FileDialogData,
  ) { }

  ngOnInit(): void { }

}
