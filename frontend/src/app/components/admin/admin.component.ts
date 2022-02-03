import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { Report } from "../../models/report";
import { ReportService } from "../../services/report.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {
  reports$: Observable<Report[]>;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.reports$ = this.reportService.getReports();
  }

  dismissReport(id): void {
    this.reportService.dismissReport(id).subscribe();
  }
  submitReport(id): void {
    this.reportService.submitReport(id).subscribe();
  }
}
