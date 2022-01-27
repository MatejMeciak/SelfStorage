package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.dto.DetailedReport;
import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.services.ReportService;
import com.appslab.selfstorage.services.UploadFileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private ReportService reportService;
    private UploadFileService uploadFileService;
    private UserService userService;

    public ReportController(ReportService reportService, UploadFileService uploadFileService, UserService userService) {
        this.reportService = reportService;
        this.uploadFileService = uploadFileService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public Report createReport(@RequestBody UploadedFile uploadedFile, @RequestParam String reason){
        return reportService.createReport(uploadedFile, reason);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Report> getAdminContent() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DetailedReport getCurrentReport(@PathVariable Long id){
        Report report = reportService.getCurrentReport(id);
        DetailedReport detailedReport = new DetailedReport();
        detailedReport.setUploadedFile(uploadFileService.findFileById(report.getFileId()).get());
        detailedReport.setReason(report.getReason());
        detailedReport.setCreator(userService.getUser());
        detailedReport.setDate(report.getCreatedDate());
        return detailedReport;
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Report removeReport(@PathVariable Long id){
        return reportService.removeReport(id);
    }

    @DeleteMapping("/submit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UploadedFile submitReport(@PathVariable Long id){
        return reportService.submitReport(id);
    }
}
