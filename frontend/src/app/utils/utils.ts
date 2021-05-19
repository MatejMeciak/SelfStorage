import { File } from '../models/file';
import { environment } from '../../environments/environment';

export const getFileUrl = (file: File): string => {
  return file.link ? file.link :
    file.mimeType.includes('image') ?
      `${environment.apiUrl}/file/${file.id}` :
        './assets/images/file_icon.png' ;
};
