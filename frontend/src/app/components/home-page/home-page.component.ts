import { Component, OnInit } from '@angular/core';
import { load } from '../../material/animations';
import { FileService } from '../../services/file.service';
import { File } from '../../models/file';
import {MatDialog} from '@angular/material/dialog';
import {UploadFileDialogComponent} from '../file-component/upload-file-dialog/upload-file-dialog.component';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
  animations: [ load ]
})
export class HomePageComponent implements OnInit {
  files: File[];
  selectedFile: File;
  fileLink: File = { fileName: 'customFile', id: null, sizeFile: null, date: null, link: '', access: false };
  constructor(private fileService: FileService,  private dialog: MatDialog) { }

  ngOnInit(): void {
    this.fileService.getUserFiles().subscribe(files => this.files = files.slice(0, 5));
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).subscribe(file => this.files.push(file));
    }
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(UploadFileDialogComponent, { data: this.fileLink });
    dialogRef.afterClosed().subscribe(file => {
      this.uploadLinkFile(file);
    });
  }
  uploadLinkFile(file): void {
    this.fileService.uploadLinkFile(file).subscribe();
  }

}
