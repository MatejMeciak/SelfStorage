import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import { File as FileModel } from "../models/file";
import { HttpClient } from "@angular/common/http";
import { Report } from "../models/report";

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  reportUrl = `${environment.apiUrl}/report`;

  constructor(private http: HttpClient) { }

  // GET
  getReports(): Observable<Report[]> {
    return this.http.get<Report[]>(this.reportUrl);
  }
  getReport(id: number): Observable<Report> {
    return this.http.get<Report>(`${this.reportUrl}/${id}`);
  }

  // POST
  createReport(fileId: number, reason: string): Observable<Report> {
    return this.http.post<Report>(`${this.reportUrl}/create?fileId=${fileId}&reason=${reason}`, {});
  }

  //DELETE
  submitReport(id: number): Observable<FileModel> {
    return this.http.delete<FileModel>(`${this.reportUrl}/submit/${id}`);
  }
  dismissReport(id: number): Observable<Report> {
    return this.http.delete<Report>(`${this.reportUrl}/remove/${id}`);
  }
}
