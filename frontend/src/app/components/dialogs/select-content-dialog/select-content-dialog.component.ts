import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Folder } from '../../../models/folder';
import { FolderService } from "../../../services/folder.service";
import { Observable } from "rxjs";
import { CategoryService } from "../../../services/category.service";
import { Category } from "../../../models/category";

@Component({
  selector: 'app-select-content-dialog',
  templateUrl: './select-content-dialog.component.html',
  styleUrls: ['./select-content-dialog.component.scss']
})
export class SelectContentDialogComponent implements OnInit {

  content$: Observable<any>;

  constructor(
    private dialogRef: MatDialogRef<SelectContentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private folderService: FolderService,
    private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.content$ = this.data.target === 'folder' ?
      this.folderService.getFolders() : this.data.target === 'category' ?
        this.categoryService.getCategories() : this.categoryService.getCategory(this.data.content.id);
  }
}
