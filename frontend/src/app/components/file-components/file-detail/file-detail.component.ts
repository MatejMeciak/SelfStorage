import {Component, OnInit, Inject, Input} from '@angular/core';
import { File } from '../../../models/file';
import { FileService } from '../../../services/file.service';
import * as fileSaver from 'file-saver';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import { getFileUrl } from '../../../utils/utils';
import {Folder} from '../../../models/folder';
import {FolderService} from "../../../services/folder.service";

@Component({
  selector: 'app-file-detail',
  templateUrl: './file-detail.component.html',
  styleUrls: ['./file-detail.component.scss']
})
export class FileDetailComponent implements OnInit {

  @Input() file: File;
  constructor(
    private fileService: FileService, private folderService: FolderService,
    private dialog: MatDialog
  ) { }

  get fileUrl(): string {
    return getFileUrl(this.file);
  }
  get fileType(): string {
    console.log(this.file.mimeType.split('/'));
    return this.file.mimeType.split('/')[1];
  }

  ngOnInit(): void { }
}
