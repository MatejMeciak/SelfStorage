import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UtilsService {
   getFileUrl(content, image?): string {
    return !content.hasOwnProperty('mimeType') ? './assets/images/folder_icon.png' :
      content.mimeType.includes('image') ? image :
        './assets/images/file_icon.png';
  };
}


