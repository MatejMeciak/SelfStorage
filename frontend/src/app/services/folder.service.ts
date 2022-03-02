import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { File as FileModel } from "../models/file";
import { Folder } from "../models/folder";

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
    return this.http.get<Folder>(`${this.folderUrl}/${id}`);
  }
  getFolderContent(id: number): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(`${this.folderUrl}/${id}/content`);
  }
  searchFolders(keyword: string): Observable<Folder[]> {
    return this.http.get<Folder[]>(`${this.folderUrl}/search?keyword=${keyword}`);
  }
  getPublicFolders(): Observable<Folder[]> {
    return this.http.get<Folder[]>(`${this.folderUrl}/public`);
  }

  // POST
  createFolder(folderName: string): Observable<Folder[]> {
    return this.http.post<Folder[]>(`${this.folderUrl}?name=${folderName}`, {});
  }
  addFileToFolder(folderId: number, fileId: number): Observable<Folder> {
    return this.http.post<Folder>(`${this.folderUrl}/${folderId}/addFile?fileId=${fileId}`, {});
  }
  // PUT
  editFolder(folder: Folder): Observable<Folder> {

    return this.http.put<Folder>(`${this.folderUrl}/${folder.id}/edit`, folder);
  }
  shareFolderWithFriends(folderId: number, email: string): Observable<Folder> {
    return this.http.put<Folder>(`${this.folderUrl}/${folderId}/share?email=${email}`, {});
  }
  getSharedFolders(): Observable<Folder[]> {
    return this.http.get<Folder[]>(`${this.folderUrl}/shared/myFolders`, {});
  }
  getFoldersFromFriends(): Observable<Folder[]> {
    return this.http.get<Folder[]>(`${this.folderUrl}/shared/fromFriends`, {});
  }

  //DELETE
  deleteFolder(id: number): Observable<Folder> {
    return this.http.delete<Folder>(`${this.folderUrl}/${id}`);
  }
  deleteFileFromFolder(folderId: number, fileId: number): Observable<Folder> {
    return this.http.delete<Folder>(`${this.folderUrl}/${folderId}/file?fileId=${fileId}`);
  }
}
