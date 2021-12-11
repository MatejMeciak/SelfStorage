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
  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoryUrl);
  }
  getCategory(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.categoryUrl}/${id}`);
  }

  // POST
  createCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${this.categoryUrl}`, category.name);
  }
  addContentToCategory(id: number, file: File): any {
    return this.http.post(`${this.categoryUrl}/${id}/add`, file.id);
  }
  // DELETE
  deleteCategory(category: Category): Observable<Category> {
    return this.http.delete<Category>(`${this.categoryUrl}/${category.id}`);
  }
  deleteContentFromCategory(category: Category): Observable<Category> {
    return this.http.delete<Category>(`${this.categoryUrl}/content/${category.id}`);
  }
}
