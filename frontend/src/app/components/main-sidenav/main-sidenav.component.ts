import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import {CategoryService} from "../../services/category.service";
import {map, Observable, Subject, takeUntil} from "rxjs";
import {FileService} from "../../services/file.service";
import {CreateFolderDialogComponent} from "../dialogs/create-folder-dialog/create-folder-dialog.component";
import {Folder} from "../../models/folder";
import {MatDialog} from "@angular/material/dialog";
import {FolderService} from "../../services/folder.service";
import {Category} from "../../models/category";
import {SidenavService} from "../../services/sidenav.service";
import { TokenStorageService } from "../../services/token-storage.service";

@Component({
  selector: 'app-main-sidenav',
  templateUrl: './main-sidenav.component.html',
  styleUrls: ['./main-sidenav.component.scss']
})
export class MainSidenavComponent implements OnInit, OnDestroy {
  @ViewChild('sidenav', { static: true }) sideNav: MatSidenav;
  isLoggedIn = false;

  unsubscribe$ = new Subject();

  categories: Observable<Category[]> = new Observable<Category[]>();
  routerLinkList = [
    { title: 'All files', link: 'files', matIcon: 'file_copy' },
    { title: 'Folders', link: 'folders', matIcon: 'folder' },
    { title: 'Category', link: 'files', matIcon: 'category', sub: [] },
    { title: 'Shared with', link: 'search', matIcon: 'folder_shared' },
  ];

  constructor(private dialog: MatDialog,
              private router: Router,
              private fileService: FileService,
              private tokenStorageService: TokenStorageService,
              private categoryService: CategoryService,
              private sidenavService: SidenavService) { }
  ngOnInit(): void {
    this.categories = this.categoryService.getCategories();
    this.sidenavService.setMainSidenav(this.sideNav);
    this.isLoggedIn = !!this.tokenStorageService.getToken();
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe();
    }
  }


  async toggleSideNav(): Promise<void> {
    await this.sidenavService.toggleMainSidenav();
  }
  ngOnDestroy(): void {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }
}
