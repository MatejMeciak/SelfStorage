import { Component, OnInit } from '@angular/core';
import { FileService } from '../../services/file.service';
import { File } from '../../models/file';
import {MatDialog} from '@angular/material/dialog';
import {UploadFileDialogComponent} from '../file-component/dialogs/upload-file-dialog/upload-file-dialog.component';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  files: File[];
  selectedFile: File;
  constructor(private fileService: FileService,  private dialog: MatDialog) { }

  ngOnInit(): void {
    this.fileService.getUserFiles().subscribe(files => this.files = files.slice(0, 5));
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
    this.fileService.uploadLinkFile(file).subscribe();
  }

}
