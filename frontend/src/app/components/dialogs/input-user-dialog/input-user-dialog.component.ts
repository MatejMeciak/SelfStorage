import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: 'app-input-user-dialog',
  templateUrl: './input-user-dialog.component.html',
  styleUrls: ['./input-user-dialog.component.scss']
})
export class InputUserDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<InputUserDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit(): void { }
}
