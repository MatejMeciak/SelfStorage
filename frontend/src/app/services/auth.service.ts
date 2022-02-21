import { Injectable } from '@angular/core';

import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from "../models/user";

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

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(this.userUrl , httpOptions);
  }
  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    return this.http.put(this.userUrl + '/changePassword' , { oldPassword: oldPassword, newPassword: newPassword })
  }
  getUserFriends(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl + '/friends');
  }
  // TODO dat do samostatnej service
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl + '/listUsers');
  }
  getUserSpace(): Observable<any[]> {
    return this.http.get<any[]>(this.userUrl + '/storageSpace');
  }
  setUserSpace(spaceSize: number, userId: number): Observable<User[]> {
    return this.http.post<User[]>(this.userUrl + '/setSpace' +`?sizeSpace=${spaceSize}&userId=${userId}`, {});
  }
}
