import { Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { Subject, takeUntil } from "rxjs";

import { FileService } from '../../../services/file.service';
import { FolderService } from "../../../services/folder.service";
import { SidenavService } from "../../../services/sidenav.service";

import { MatDrawer } from "@angular/material/sidenav";
import { Folder } from "../../../models/folder";
import { DialogService } from "../../../services/dialog.service";
import { Category } from "../../../models/category";
import { CategoryService } from "../../../services/category.service";


@Component({
  selector: 'app-content-page',
  templateUrl: './content-page.component.html',
  styleUrls: ['./content-page.component.scss']
})
export class ContentPageComponent implements OnInit, OnDestroy {
  @ViewChild('detailSidenav', { static: true }) public detailSidenav: MatDrawer;

  unsubscribe$ = new Subject();
  constructor(private fileService: FileService,
              private dialogService: DialogService,
              private folderService: FolderService,
              private categoryService: CategoryService,
              private sidenavService: SidenavService) { }

  ngOnInit(): void {
    this.sidenavService.setDetailSidenav(this.detailSidenav);
  }

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
