import { Component, OnInit } from '@angular/core';
import {File} from "../../models/file";
import {FileService} from "../../services/file.service";
import {MatDialog} from "@angular/material/dialog";
import {UploadFileDialogComponent} from "../dialogs/upload-file-dialog/upload-file-dialog.component";

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.scss']
})
export class WelcomePageComponent implements OnInit {

  files: File[];
  selectedFile: File;
  constructor(private fileService: FileService,  private dialog: MatDialog) { }

  ngOnInit(): void {
    this.fileService.getFiles().subscribe(files => this.files = files.slice(0, 5));
  }

  onFileInput(files: FileList): void {

  }
  openDialog(): void {
    const dialogRef = this.dialog.open(UploadFileDialogComponent, { data: { } as File });
    dialogRef.afterClosed().subscribe(file => {
      this.uploadLinkFile(file);
    });
  }
  uploadLinkFile(file): void {
    this.fileService.uploadFile(file).subscribe();
  }
}
