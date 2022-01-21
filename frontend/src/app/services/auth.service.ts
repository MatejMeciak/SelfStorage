import { Injectable } from '@angular/core';

import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import {User} from "../models/user";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  userUrl = `${environment.apiUrl}/user`;
  authUrl = `${environment.authApi}`;
  token: BehaviorSubject<string> = new BehaviorSubject(null);
  constructor(private readonly http: HttpClient) { }
  getToken(): string {
    return sessionStorage.getItem('auth-token');
  }
  isLoggedIn(): boolean {
    //return this.getCurrentUser();
    return !!this.getToken();
  }

  login(credentials): Observable<any> {
    return this.http.post(this.authUrl + 'signin', {
      email: credentials.email,
      password: credentials.password
    }, httpOptions);
  }
  register(user): Observable<any> {
    return this.http.post(this.authUrl + 'signup', {
      username: user.username,
      email: user.email,
      password: user.password,
      matchingPassword: user.matchingPassword,
      socialProvider: 'LOCAL'
    }, httpOptions);
  }

  getCurrentUser(): Observable<any> {
    return this.http.get(this.userUrl + '/me', httpOptions);
  }
}
