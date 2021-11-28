import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { File as FileModel } from "../models/file";
import { Folder } from "../models/folder";
import {Link} from "../models/link";

@Injectable({
  providedIn: 'root'
})
export class FolderService {
  folderUrl = `${environment.apiUrl}/folder`;

  constructor(private http: HttpClient) { }

  // GET
  getFolders(): Observable<Folder[]> {
    return this.http.get<Folder[]>(`${this.folderUrl}/allFolder`);
  }
  getFolder(id: number): Observable<Folder> {
    return this.http.get<Folder>(`${this.folderUrl}/getFolder/${id}`);
  }
  getFolderContent(id: number): Observable<any> {
    return this.http.get(`${this.folderUrl}/${id}/content`);
  }
  searchFolders(keyword: string): Observable<Folder[]> {
    return this.http.get<Folder[]>(`${this.folderUrl}/search/?keyword=${keyword}`);
  }

  // POST
  createFolder(folder: Folder): Observable<Folder[]> {
    return this.http.post<Folder[]>(this.folderUrl, folder);
  }

  // PUT
  updateFolderWithFile(folderId: Folder, file: FileModel): Observable<any> {
    delete file['customUsers'];
    return this.http.put(`${this.folderUrl}/${folderId}/file`, file);
  }
  // TODO
  updateFolderWithLink(folderId: Folder, file: FileModel): Observable<Folder[]> {
    delete file['customUsers'];
    return this.http.put<Folder[]>(`${this.folderUrl}/${folderId}`, file);
  }

  //DELETE
  deleteFolder(id: number): Observable<Folder> {
    return this.http.delete<Folder>(`${this.folderUrl}/${id}`);
  }
  deleteContentInFolder(folderId: number, fileId): Observable<Folder> {
    return this.http.delete<Folder>(`${this.folderUrl}/${fileId}/?folderId=${folderId}`);
  }
}
