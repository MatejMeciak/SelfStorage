import { Injectable } from "@angular/core";
import { fromEvent, map, mergeMap, Observable, of, shareReplay } from "rxjs";
import { FileService } from "./file.service";

interface ImageDictionary {
  [fileId: number]: Observable<string>;
}

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  loadedImages: ImageDictionary = {};

  constructor(private fileService: FileService) { }

  getImageForFile(fileId: number): Observable<string> {
    if (fileId in this.loadedImages) {
      return this.loadedImages[fileId];
    }

    const image = this.fileService.getFileBlob(fileId).pipe(
      mergeMap(blob => this.createImageFromBlob(blob)),
      shareReplay(1),
    );

    this.loadedImages[fileId] = image;
    return image;
  }

  createImageFromBlob(blob: Blob): Observable<string> {
    if (!blob) {
      return of('assets/images/file_icon.png');
    }

    let reader = new FileReader();
    reader.readAsDataURL(blob);
    return fromEvent(reader, 'load').pipe(
      map(() => reader.result as string)
    );
  }

}
