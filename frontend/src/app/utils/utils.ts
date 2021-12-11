import { environment } from '../../environments/environment';

export const getFileUrl = (content): string => {
  return !content.hasOwnProperty('mimeType') ? './assets/images/folder_icon.png' :
    content.mimeType.includes('image') ? `${environment.apiUrl}/file/${content.id}` :
      './assets/images/file_icon.png';
 };
