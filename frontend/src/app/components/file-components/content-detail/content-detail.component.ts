import {Component, OnInit, Inject, Input} from '@angular/core';
import { File } from '../../../models/file';
import { FileService } from '../../../services/file.service';
import * as fileSaver from 'file-saver';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Folder} from '../../../models/folder';
import {FolderService} from "../../../services/folder.service";
import {SidenavService} from "../../../services/sidenav.service";
import { fromEvent } from "rxjs";

@Component({
  selector: 'app-content-detail',
  templateUrl: './content-detail.component.html',
  styleUrls: ['./content-detail.component.scss']
})
export class ContentDetailComponent implements OnInit {

  @Input() file: File;
  constructor(
    private fileService: FileService, private folderService: FolderService,
    private dialog: MatDialog, private sidenavService: SidenavService
  ) { }

  get fileUrl(): string {
    return ''
  }
  get fileType(): string {
    console.log(this.file.mimeType.split('/'));
    return this.file.mimeType.split('/')[1];
  }

  ngOnInit(): void {
  }
}
