import { Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { Subject, takeUntil } from "rxjs";

import { FileService } from '../../../services/file.service';
import { FolderService } from "../../../services/folder.service";
import { SidenavService } from "../../../services/sidenav.service";

import { MatDrawer } from "@angular/material/sidenav";


@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit, OnDestroy {
  @ViewChild('detailSidenav', { static: true }) public detailSidenav: MatDrawer;

  unsubscribe$ = new Subject();
  constructor(private fileService: FileService,
              private folderService: FolderService,
              private sidenavService: SidenavService) { }

  ngOnInit(): void {
    this.sidenavService.setDetailSidenav(this.detailSidenav);
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe();
    }
  }


  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}
