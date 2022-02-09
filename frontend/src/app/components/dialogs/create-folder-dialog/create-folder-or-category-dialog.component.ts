import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-create-folder-or-category-dialog',
  templateUrl: './create-folder-or-category-dialog.component.html',
  styleUrls: ['./create-folder-or-category-dialog.component.scss']
})
export class CreateFolderOrCategoryDialogComponent implements OnInit {

  constructor(
    private dialogRef: MatDialogRef<CreateFolderOrCategoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data
  ) { }

  ngOnInit(): void { }
}
