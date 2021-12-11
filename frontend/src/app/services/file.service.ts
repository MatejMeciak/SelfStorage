import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { File as FileModel } from '../models/file';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  fileUrl = `${environment.apiUrl}/file`;

  constructor(private http: HttpClient) { }

  // GET
  getFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(this.fileUrl);
  }
  getFile(id: number): Observable<FileModel> {
    return this.http.get<FileModel>(`${this.fileUrl}/${id}`);
  }
  getSearchedFiles(keyword: string): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/search/?keyword=${keyword}`);
  }
  getPublicFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/public`);
  }
  getSearchedPublicFiles(keyword: string): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/public/search/?keyword=${keyword}`);
  }
  getSharedFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/share/myFiles`);
  }
  downloadFile(file: FileModel): Observable<Blob> {
    return this.http.get(`${this.fileUrl}/${file.id}`, { responseType: 'blob' });
  }

  // POST
  uploadFile(file: File): Observable<FileModel> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<FileModel>(this.fileUrl, formData);
  }

  // DELETE
  deleteFile(file: FileModel): Observable<FileModel> {
    return this.http.delete<FileModel>(`${this.fileUrl}/${file.id}`);
  }

  // PUT
  updateFile(file: FileModel): Observable<FileModel> {
    return this.http.put<FileModel>(`${this.fileUrl}/edit`, file);
  }
  shareFileWithUser(username: string, file:FileModel): Observable<FileModel> {
    return this.http.put<FileModel>(`${this.fileUrl}/share/?username=${username}`, file)
  }

}
