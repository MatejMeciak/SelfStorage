package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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
    public Report getCurrentReport(@RequestParam Long id){
        return reportService.getCurrentReport(id);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public Report removeReport(@RequestBody Report report){
        return reportService.removeReport(report);
    }

    @DeleteMapping("/submit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UploadedFile submitReport(@RequestParam Long id){
        return reportService.submitReport(id);
    }
}
