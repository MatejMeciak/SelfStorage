import { Component, OnDestroy, OnInit } from '@angular/core';
import { FileService } from "../../../services/file.service";
import { Observable, of, Subject } from "rxjs";
import { File } from "../../../models/file";
import { ImageService } from "../../../services/image.service";

@Component({
  selector: 'app-file-detail',
  templateUrl: './file-detail.component.html',
  styleUrls: ['./file-detail.component.scss']
})
export class FileDetailComponent implements OnInit, OnDestroy {
  file: File;

  unsubscribe$ = new Subject();
  constructor(private fileService: FileService,
              private imageService: ImageService) { }

  ngOnInit(): void {
    this.fileService.getSelectedFile().subscribe(file => this.file = file);
  }

  getImage(file: File): Observable<string> {
    return file.mimeType.includes('image') ?
      this.imageService.getImageForFile(file.id) :
      of('assets/images/file_icon.png');
  }
  fileType(file: File) {
    return file.mimeType.split('/')[1];
  }

  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}
