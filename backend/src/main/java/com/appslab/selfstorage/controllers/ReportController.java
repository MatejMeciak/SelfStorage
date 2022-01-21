package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok(ReportService.class.cast(reportService.getAllReports()));
    }

    @PutMapping("/remove/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Report removeReport(@RequestParam Long id){
        return reportService.removeReport(id);
    }

    @DeleteMapping("/submit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UploadedFile submitReport(@RequestParam Long id){
        return reportService.submitReport(id);
    }
}
