import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { File } from '../models/file';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  fileUrl = 'http://localhost:8080/api/file';

  constructor(private http: HttpClient) { }

  getFiles(): Observable<File[]> {
    return this.http.get<File[]>(this.fileUrl);
  }
  getFile(id: number): Observable<File> {
    return this.http.get<File>(this.fileUrl);
  }
  // (file: File): Observable<File> {
  //  return this.http.post<File>(this.fileUrl);
  // }
  // updateFile(file: File): Observable<File> {
  //  return this.http.put<File>(this.fileUrl);
  // }
}
