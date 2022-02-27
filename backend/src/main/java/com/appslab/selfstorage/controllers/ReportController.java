package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.dto.DetailedReport;
import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.services.ReportService;
import com.appslab.selfstorage.services.FileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private ReportService reportService;
    private FileService fileService;
    private UserService userService;

    public ReportController(ReportService reportService, FileService fileService, UserService userService) {
        this.reportService = reportService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DetailedReport getCurrentReport(@PathVariable Long id){
        Report report = reportService.getCurrentReport(id);
        DetailedReport detailedReport = new DetailedReport();
        detailedReport.setFile(fileService.findFileById(report.getFileId()).get());
        detailedReport.setReason(report.getReason());
        detailedReport.setCreator(userService.getUser());
        detailedReport.setDate(report.getCreatedDate());
        return detailedReport;
    }

    @PostMapping("/create")
    public Report createReport(@RequestParam Long fileId, @RequestParam String reason){
        return reportService.createReport(fileId, reason);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Report removeReport(@PathVariable Long id){
        return reportService.removeReport(id);
    }

    @DeleteMapping("/submit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public File submitReport(@PathVariable Long id){
        return reportService.submitReport(id);
    }
}
