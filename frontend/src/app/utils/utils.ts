import { File } from '../models/file';
import { environment } from '../../environments/environment';

export const getFileUrl = (file: File): string => {
  return file.mimeType.includes('image') ?
       `${environment.apiUrl}/file/${file.id}` :
         './assets/images/file_icon.png' ;
 };
