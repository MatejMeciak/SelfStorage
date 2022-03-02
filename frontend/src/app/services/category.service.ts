import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

import {HttpClient} from "@angular/common/http";
import {Category} from "../models/category";
import {File} from "../models/file";
import { Folder } from "../models/folder";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  categoryUrl = `${environment.apiUrl}/category`;

  constructor(private http: HttpClient) { }

  // GET
  getCategory(contentId: number): Observable<any> {
    return this.http.get<any>(`${this.categoryUrl}/${contentId}`);
  }
  getCategories(): Observable<any[]> {
    return this.http.get<any[]>(`${this.categoryUrl}/list`);
  }
  getFilesInCategory(categoryId: number): Observable<File[]> {
    return this.http.get<File[]>(`${this.categoryUrl}/content/files?id=${categoryId}`);
  }
  getFoldersInCategory(categoryId: number): Observable<Folder[]> {
    return this.http.get<Folder[]>(`${this.categoryUrl}/content/folders?id=${categoryId}`);
  }

  // POST
  createCategory(categoryName: string): Observable<Category> {
    return this.http.post<Category>(`${this.categoryUrl}?name=${categoryName}`, {});
  }
  addContentToCategory(id: number, contentId: number): Observable<Category> {
    return this.http.post<Category>(`${this.categoryUrl}/${id}/add?requestId=${contentId}`, {});
  }
  // DELETE
  deleteCategory(categoryId: number): Observable<Category> {
    return this.http.delete<Category>(`${this.categoryUrl}/${categoryId}`);
  }
  deleteContentFromCategory(categoryId: number, contentId: number): Observable<Category> {
    return this.http.delete<Category>(`${this.categoryUrl}/${categoryId}/content?id=${contentId}`);
  }
}
