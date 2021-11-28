import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { Link } from "../models/link";

@Injectable({
  providedIn: 'root'
})
export class LinkService {
  linkUrl = `${environment.apiUrl}/link`;

  constructor(private http: HttpClient) { }

  // GET
  getLinkList(): Observable<Link[]> {
    return this.http.get<Link[]>(this.linkUrl);
  }
  // TODO on backend
  getLink(id: number): Observable<Link> {
    return this.http.get<Link>(`${this.linkUrl}/${id}`);
  }
  getSearchedLinkList(keyword: string): Observable<Link[]> {
    return this.http.get<Link[]>(`${this.linkUrl}/search/?keyword=${keyword}`);
  }

  // POST
  uploadLink(link: Link): Observable<Link> {
    return this.http.post<Link>(this.linkUrl, link);
  }

  // DELETE
  deleteLink(link: Link): Observable<Link> {
    return this.http.delete<Link>(`${this.linkUrl}/${link.id}`);
  }

  // PUT
  updateLink(link: Link): Observable<Link> {
    return this.http.put<Link>(`${this.linkUrl}/edit`, link);
  }
}
