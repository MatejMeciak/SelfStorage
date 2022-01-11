import { Injectable } from '@angular/core';

import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { tap } from 'rxjs/operators';
import {User} from '../models/user';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  userUrl = `${environment.apiUrl}/user`;
  authUrl = `${environment.apiUrl}`;
  token: BehaviorSubject<string> = new BehaviorSubject(null);
  constructor(private readonly http: HttpClient) { }
  getToken(): string {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
  //
  // login(username: string, password: string): Observable<any> {
  //   const info = btoa(`${username}:${password}`);
  //   const token = `Basic ${info}`;
  //   const options = {
  //     headers: new HttpHeaders({
  //       Authorization: token,
  //       'X-Requested-With' : 'XMLHttpRequest'
  //     }),
  //     withCredentials: true
  //   };
  //   return this.http.get(`${this.authUrl}/file`, options).pipe(
  //     tap(() => localStorage.setItem('token', token))
  //   );
  // }
  // getUser(): Observable<User> {
  //   return this.http.get<User>(this.userUrl);
  // }
  // logout(): void {
  //   localStorage.removeItem('token');
  // }
  // register(username: string, password: string, email: string): Observable<any> {
  //   const user = { username, password, email };
  //   return this.http.post(`${this.authUrl}/registration`, user);
  // }
  login(credentials): Observable<any> {
    return this.http.post(environment.AUTH_API + 'signin', {
      email: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user): Observable<any> {
    return this.http.post(environment.AUTH_API + 'signup', {
      username: user.username,
      email: user.email,
      password: user.password,
      socialProvider: 'LOCAL'
    }, httpOptions);
  }
}
