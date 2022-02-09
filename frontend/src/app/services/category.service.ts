import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

import {HttpClient} from "@angular/common/http";
import {Category} from "../models/category";
import {File} from "../models/file";

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
  getCategoryContent(category: string): Observable<File[]> {
    return this.http.get<File[]>(`${this.categoryUrl}?category=${category}`);
  }

  // POST
  createCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${this.categoryUrl}?name=${category.name}`, {});
  }
  addContentToCategory(id: number, fileId: number): Observable<Category> {
    return this.http.post<Category>(`${this.categoryUrl}/${id}/add?requestId=${fileId}`, {});
  }
  // DELETE
  deleteCategory(category: Category): Observable<Category> {
    return this.http.delete<Category>(`${this.categoryUrl}/${category.id}`);
  }
  deleteContentFromCategory(category: Category): Observable<Category> {
    return this.http.delete<Category>(`${this.categoryUrl}/content/${category.id}`);
  }
}
