import { Injectable } from '@angular/core';
import { CreateFolderOrCategoryDialogComponent } from "../components/dialogs/create-folder-dialog/create-folder-or-category-dialog.component";
import { Folder } from "../models/folder";
import { File } from "../models/file";
import { EditFileDialogComponent } from "../components/dialogs/edit-file-dialog/edit-file-dialog.component";
import { SelectContentDialogComponent } from "../components/dialogs/select-content-dialog/select-content-dialog.component";
import { MatDialog } from "@angular/material/dialog";
import { OpenContentDialogComponent } from "../components/dialogs/open-content-dialog/open-content-dialog.component";
import { FileDialogData } from "../models/FileDialogData";
import { Observable } from "rxjs";
import { ConfirmDialogComponent } from "../components/dialogs/confirm-dialog/confirm-dialog.component";
import { InputUserDialogComponent } from "../components/dialogs/input-user-dialog/input-user-dialog.component";
import { Category } from "../models/category";

@Injectable({
  providedIn: 'root'
})
export class DialogService {
  constructor(private dialog: MatDialog) { }

  confirmDialog(content: File | Folder, action: string): Observable<any> {
    return this.dialog.open(ConfirmDialogComponent,
      { data: { content: content, action: action } }
    ).afterClosed();
  }
  editContentDialog(content: File | Folder): Observable<any> {
    return this.dialog.open(EditFileDialogComponent,
      { data: content }
    ).afterClosed();
  }
  openViewContentDialog(dialogData: FileDialogData): Observable<any> {
    return this.dialog.open(OpenContentDialogComponent,
      { data: dialogData, panelClass: 'custom-dialog' }
    ).afterClosed();
  }
  shareWithUserDialog(): Observable<any> {
    return this.dialog.open(InputUserDialogComponent).afterClosed();
  }
  selectContentDialog(content= {}, target: string): Observable<any> {
    return this.dialog.open(SelectContentDialogComponent,
      { data: { content: content, selected: {}, target: target } }
    ).afterClosed();
  }
  createFolderOrCategoryDialog(content: Folder | Category, type: string): Observable<any> {
    return this.dialog.open(CreateFolderOrCategoryDialogComponent,
      { data: { content: content, type: type } }
    ).afterClosed();
  }
}
