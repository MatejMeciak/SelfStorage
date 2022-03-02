import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { File as FileModel } from '../models/file';
import { Category } from "../models/category";

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
  getAllFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(this.fileUrl);
  }
  getPublicFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/public`);
  }
  getPublicFile(id: number): Observable<FileModel> {
    return this.http.get<FileModel>(`${this.fileUrl}/public/${id}`);
  }
  getPublicFileBlob(id: number): Observable<Blob> {
    return this.http.get(`${this.fileUrl}/public/${id}`, { responseType: 'blob' });
  }
  getFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/files`);
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
  getSearchedPublicFiles(keyword: string): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/public/search?keyword=${keyword}`);
  }
  getSharedFiles(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/shared/myFiles`);
  }
  getSharedFilesWith(email: string): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/shared/myFiles`);
  }
  getFilesFromFriends(): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.fileUrl}/shared/fromFriends`);
  }
  fileCategories(fileId: number): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.fileUrl}/categories?id=${fileId}`);
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
    delete file['owner'];
    return this.http.put<FileModel>(`${this.fileUrl}/edit`, file);
  }
  shareFileWithUser(email: string, file:FileModel): Observable<FileModel> {
    return this.http.put<FileModel>(`${this.fileUrl}/${file.id}/share?email=${email}`, {})
  }
}
