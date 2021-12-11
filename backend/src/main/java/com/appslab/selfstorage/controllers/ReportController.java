package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.services.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/create")
    public void createReport(@RequestBody UploadedFile uploadedFile, @RequestParam String reason){
        reportService.createReport(uploadedFile, reason);
    }
}
