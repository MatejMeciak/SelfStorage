import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from "rxjs";
import { FileService } from "../../../services/file.service";
import { DialogService } from "../../../services/dialog.service";
import { FolderService } from "../../../services/folder.service";
import { CategoryService } from "../../../services/category.service";
import { Category } from "../../../models/category";
import { Folder } from "../../../models/folder";

@Component({
  selector: 'app-content-header',
  templateUrl: './content-header.component.html',
  styleUrls: ['./content-header.component.scss']
})
export class ContentHeaderComponent implements OnInit, OnDestroy {
  @Input() title: string;
  @Input() actions: boolean = true;

  unsubscribe$ = new Subject();
  constructor(private fileService: FileService,
              private dialogService: DialogService,
              private folderService: FolderService,
              private categoryService: CategoryService) { }

  ngOnInit(): void { }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe(() => {
        location.reload();
      });
    }
  }
  createFolder(): void {
    this.dialogService.createFolderOrCategoryDialog({ name: '' } as Folder, 'folder').subscribe(result => {
      if (result) {
        this.folderService.createFolder(result).subscribe(() => location.reload());
      }
    });
  }
  createCategory(): void {
    this.dialogService.createFolderOrCategoryDialog({ name: '' } as Category, 'category').subscribe(result => {
      if (result) {
        this.categoryService.createCategory(result).subscribe(() => location.reload());
      }
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}
