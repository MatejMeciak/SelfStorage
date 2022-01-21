import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { Observable, Subject, takeUntil } from "rxjs";

import { FileService } from '../../../services/file.service';
import { FolderService } from "../../../services/folder.service";

import { File } from '../../../models/file';
import { Folder } from '../../../models/folder';

import { CreateFolderDialogComponent } from '../../dialogs/create-folder-dialog/create-folder-dialog.component';
import { ContentDetailComponent } from "../content-detail/content-detail.component";

import { MatDialog } from '@angular/material/dialog';
import {MatDrawer} from "@angular/material/sidenav";
import {SidenavService} from "../../../services/sidenav.service";

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.scss']
})
export class FilesComponent implements OnInit, OnDestroy {
  @ViewChild('detailSidenav', { static: true }) public detailSidenav: MatDrawer;

  selectedFile: File;
  files: File[];
  folders$:  Observable<Folder[]>;

  unsubscribe$ = new Subject();
  constructor(private fileService: FileService,
              private folderService: FolderService,
              private sidenavService:SidenavService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.fileService.getFiles().pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe(files => this.files = files);
    this.folders$ = this.folderService.getFolders();
    this.sidenavService.setDetailSidenav(this.detailSidenav);
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe(file => this.files.push(file));
    }
  }

  openFolderDialog(): void {
    const dialogRef = this.dialog.open(CreateFolderDialogComponent, { data: { name: '', access: false } as Folder });
    dialogRef.afterClosed().subscribe(folder => {
      this.folderService.createFolder(folder).subscribe();
      this.folders$ = this.folderService.getFolders();
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }
}
