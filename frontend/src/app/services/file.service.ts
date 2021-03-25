import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { File as FileModel } from '../models/file';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  fileUrl = 'http://localhost:8080/file';

  constructor(private http: HttpClient) { }

  getFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(this.fileUrl);
  }
  getFile(id: number): Observable<FileModel> {
    return this.http.get<FileModel>(`${this.fileUrl}/${id}`);
  }
  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(this.fileUrl, formData);
  }
  updateFile(file: FileModel): Observable<FileModel> {
    return this.http.put<FileModel>(this.fileUrl, file);
  }
}
