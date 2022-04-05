import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { CategoryService } from "../../services/category.service";
import { Observable, Subject, takeUntil } from "rxjs";
import { FileService } from "../../services/file.service";

import { Category } from "../../models/category";
import { SidenavService } from "../../services/sidenav.service";
import { TokenStorageService } from "../../services/token-storage.service";
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";
import { DialogService } from "../../services/dialog.service";
import { Folder } from "../../models/folder";
import { FolderService } from "../../services/folder.service";
import { UserService } from "../../services/user.service";

@Component({
  selector: 'app-main-sidenav',
  templateUrl: './main-sidenav.component.html',
  styleUrls: ['./main-sidenav.component.scss']
})
export class MainSidenavComponent implements OnInit, OnDestroy {
  @ViewChild('sidenav', { static: true }) sideNav: MatSidenav;
  isLoggedIn = false;
  showAdminBoard = false;
  homeUlr = '/';
  storage: any;

  unsubscribe$ = new Subject();

  categories$: Observable<Category[]> = new Observable<Category[]>();
  routerLinkList = [
    { title: 'Storage', link: 'storage', matIcon: 'source' },
    { title: 'All files', link: 'allFiles', matIcon: 'file_copy' },
    { title: 'Folders', link: 'folders', matIcon: 'folder' },
    { title: 'Category', link: '', matIcon: 'category', sub: [] },
    { title: 'Shared', link: 'sharedFrom', matIcon: 'folder_shared' },
    //{ title: 'Shared to', link: 'sharedTo', matIcon: 'folder_shared' },
  ];

  constructor(private fileService: FileService,
              private folderService: FolderService,
              private tokenStorageService: TokenStorageService,
              private categoryService: CategoryService,
              private sidenavService: SidenavService,
              private authService: AuthService,
              private userService: UserService,
              private dialogService: DialogService,
              private router: Router) { }

  ngOnInit(): void {
    this.categories$ = this.categoryService.getCategories();
    this.sidenavService.setMainSidenav(this.sideNav);
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      this.userService.getCurrentUser().pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe(user => {
        this.homeUlr = 'storage';
        this.showAdminBoard = user.roles.includes('ROLE_ADMIN');
        if (!this.showAdminBoard) {
          this.userService.getUserSpace().subscribe(storage => this.storage = storage);
        }
      });
    }
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
  redirectTo(url: string) {
    this.router.navigateByUrl(url);
  }
  createFolder(): void {
    this.dialogService.createFolderOrCategoryDialog({ name: '' } as Folder, 'folder').subscribe(result => {
      if (result) {
        this.folderService.createFolder(result.name).subscribe(() => location.reload());
      }
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}
