import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { User } from "../models/user";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../../environments/environment";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  userUrl = `${environment.apiUrl}/user`;

  constructor(private readonly http: HttpClient) { }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(this.userUrl , httpOptions);
  }
  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    return this.http.put(this.userUrl + '/changePassword' , { oldPassword: oldPassword, newPassword: newPassword })
  }
  changeUsername(username: string): Observable<User> {
    return this.http.put<User>(this.userUrl + '/changeUsername' +`?username=${username}`, {});
  }
  getUserFriends(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl + '/friends');
  }
  // TODO dat do samostatnej service
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl + '/listUsers');
  }
  getUserSpace(): Observable<any> {
    return this.http.get<any>(this.userUrl + '/storageSpace');
  }
  setUserSpace(spaceSize: number, userId: number): Observable<User[]> {
    return this.http.post<User[]>(`${this.userUrl}/${userId}` + '/setSpace' +`?sizeSpace=${spaceSize}`, {});
  }
  getProfilePicture(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/file/profilePicture/show`, { responseType: 'blob' });
  }
  setProfilePicture(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('multipartFile', file);
    return this.http.put(`${environment.apiUrl}/file/profilePicture/update`, formData);
  }

}
