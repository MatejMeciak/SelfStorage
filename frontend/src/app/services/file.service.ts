import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { File as FileModel } from '../models/file';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  fileUrl = `${environment.apiUrl}/file`;

  private selectedFile = new Subject<FileModel>();

  constructor(private http: HttpClient) { }

  getSelectedFile(): Observable<FileModel> {
    return this.selectedFile;
  }
  setSelectedFile(file: FileModel): void {
    this.selectedFile.next(file);
  }

  // GET
  getFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(this.fileUrl);
  }
  getFile(id: number): Observable<FileModel> {
    return this.http.get<FileModel>(`${this.fileUrl}/${id}`);
  }
  getFileBlob(id: number): Observable<Blob> {
    return this.http.get(`${this.fileUrl}/${id}`, { responseType: 'blob' });
  }
  getSearchedFiles(keyword: string): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/search?keyword=${keyword}`);
  }
  getPublicFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/public`);
  }
  getSearchedPublicFiles(keyword: string): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/public/search?keyword=${keyword}`);
  }
  getSharedFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/share/myFiles`);
  }

  // POST
  uploadFile(file: File): Observable<FileModel> {
    const formData = new FormData();
    formData.append('multipartFile', file);
    return this.http.post<FileModel>(this.fileUrl, formData);
  }

  // DELETE
  deleteFile(fileId: number): Observable<FileModel> {
    return this.http.delete<FileModel>(`${this.fileUrl}/${fileId}`);
  }

  // PUT
  updateFile(file: FileModel): Observable<FileModel> {
    delete file['friends'];
    return this.http.put<FileModel>(`${this.fileUrl}/edit`, file);
  }
  shareFileWithUser(username: string, file:FileModel): Observable<FileModel> {
    return this.http.put<FileModel>(`${this.fileUrl}/share/?username=${username}`, file)
  }
}
