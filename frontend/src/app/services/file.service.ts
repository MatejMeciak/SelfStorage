import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { File as FileModel } from '../models/file';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  fileUrl = `${environment.apiUrl}/file`;
  constructor(private http: HttpClient) {
  }

  getFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(this.fileUrl);
  }

  getFile(id: number): Observable<FileModel> {
    return this.http.get<FileModel>(`${this.fileUrl}/${id}`);
  }

  uploadFile(file: File): Observable<FileModel> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<FileModel>(this.fileUrl, formData);
  }

  uploadLinkFile(file: FileModel): Observable<FileModel> {
    return this.http.post<FileModel>(`${this.fileUrl}/uploadLink`, file);
  }

  updateFile(file: FileModel): Observable<FileModel> {
    return this.http.put<FileModel>(`${this.fileUrl}/edit`, file);
  }

  downloadFile(file: FileModel): Observable<Blob> {
    return this.http.get(`${this.fileUrl}/${file.id}`, { responseType: 'blob' });
  }

  searchFile(keyword: string): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/search/?keyword=${keyword}`);
  }
}
