import { Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { Subject } from "rxjs";

import { FileService } from '../../../services/file.service';
import { FolderService } from "../../../services/folder.service";
import { SidenavService } from "../../../services/sidenav.service";

import { MatDrawer } from "@angular/material/sidenav";
import { DialogService } from "../../../services/dialog.service";
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

  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}
