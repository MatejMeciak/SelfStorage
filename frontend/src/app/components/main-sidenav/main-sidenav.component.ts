import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import {CategoryService} from "../../services/category.service";
import {Subject, takeUntil} from "rxjs";
import {FileService} from "../../services/file.service";
import {CreateFolderDialogComponent} from "../dialogs/create-folder-dialog/create-folder-dialog.component";
import {Folder} from "../../models/folder";
import {MatDialog} from "@angular/material/dialog";
import {FolderService} from "../../services/folder.service";

@Component({
  selector: 'app-main-sidenav',
  templateUrl: './main-sidenav.component.html',
  styleUrls: ['./main-sidenav.component.scss']
})
export class MainSidenavComponent implements OnInit {
  @ViewChild('sidenav') sideNavRef: MatSidenav;

  unsubscribe$ = new Subject();

  routerLinkList = [
    { title: 'All files', link: 'files', matIcon: 'file_copy' },
    { title: 'Folders', link: 'folders', matIcon: 'folder' },
    { title: 'Category', link: 'files', matIcon: 'category', sub: []},
    { title: 'Public files', link: 'search', matIcon: 'public' },
    { title: 'Shared with', link: 'search', matIcon: 'folder_shared' },
  ];


  constructor(private dialog: MatDialog ,private router: Router, private fileService: FileService, private categoryService:CategoryService, private folderService: FolderService) {
    router.events.subscribe(() => {
      this.sideNavRef.close();
    });
  }
  ngOnInit(): void { }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).pipe(
        takeUntil(this.unsubscribe$)
      )//.subscribe(file => this.files.push(file));
    }
  }


  async toggleSideNav(): Promise<void> {
    await this.sideNavRef.toggle();
  }
  ngOnDestroy(): void {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }
}
