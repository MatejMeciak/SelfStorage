import { Component, OnInit } from '@angular/core';
import { Observable, of, tap } from "rxjs";
import { Report } from "../../models/report";
import { ReportService } from "../../services/report.service";
import { File } from "../../models/file";
import { FileService } from "../../services/file.service";
import { ImageService } from "../../services/image.service";
import { User } from "../../models/user";
import { AuthService } from "../../services/auth.service";
import { UserService } from "../../services/user.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {
  reports$: Observable<Report[]>;
  users$: Observable<User[]>;
  spaceNumber:number = 2;

  constructor(private reportService: ReportService,
              private authService: AuthService,
              private file: FileService,
              private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.reports$ = this.reportService.getReports();
    this.users$ = this.userService.getAllUsers();
    //this.file.setAdmin().subscribe();
  }
  changeStorageSpace(spaceSize: number, userId: number) {
    this.userService.setUserSpace(spaceSize*1000000000, userId).subscribe(() => {
      location.reload();
    });
  }
  dismissReport(id: number): void {
    this.reportService.dismissReport(id).subscribe(()=>location.reload());
  }
  submitReport(id: number): void {
    this.reportService.submitReport(id).subscribe(()=>location.reload());
  }
}
