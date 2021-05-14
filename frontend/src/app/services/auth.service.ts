import { Injectable } from '@angular/core';

import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authUrl = `${environment.apiUrl}`;
  token: BehaviorSubject<string> = new BehaviorSubject(null);
  constructor(private readonly http: HttpClient) { }
  getToken(): string {
    return this.token.getValue();
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  login(username: string, password: string): Observable<any> {
    const info = btoa(`${username}:${password}`);
    const token = `Basic ${info}`;
    const options = {
      headers: new HttpHeaders({
        Authorization: token,
        'X-Requested-With' : 'XMLHttpRequest'
      }),
      withCredentials: true
    };
    return this.http.get(`${this.authUrl}/file`, options).pipe(
      tap(() => this.token.next(token))
    );
  }

  logout(): void {
    this.token.next(null);
  }
  register(username: string, password: string, firstName: string, lastName: string): Observable<any> {
    const user = { username, password, firstName, lastName };
    return this.http.post(`${this.authUrl}/registration`, user);
  }
}
