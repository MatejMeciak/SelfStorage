import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Folder } from '../../../models/folder';
import { File } from '../../../models/file';
import { FileService } from "../../../services/file.service";
import { DomSanitizer } from '@angular/platform-browser';
import { fromEvent, Subscription } from "rxjs";
import { UtilsService } from "../../../utils/utils";

@Component({
  selector: 'app-content-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss']
})
export class ContentCardComponent implements OnInit, OnDestroy {
  @Input() content: File | Folder;
  get fileUrl(): string {
    return this.utilsService.getFileUrl(this.content, this.imageToShow);
  }
  imageToShow: any;
  subscription: Subscription;
  constructor(private fileService:FileService,  private sanitizer: DomSanitizer, private utilsService: UtilsService) { }
  ngOnInit(): void {
    this.getImageFromService();
  }
  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    if (image) {
      reader.readAsDataURL(image);
    }
    this.subscription = fromEvent(reader, 'load').subscribe( () => {
      this.imageToShow = reader.result;
    })
  }
  getImageFromService() {
    this.fileService.getFileBlob(this.content.id).subscribe(data => {
      this.createImageFromBlob(data);
    }, error => {
      console.log(error);
    });
  }
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
