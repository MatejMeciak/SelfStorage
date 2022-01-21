package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.ReportRepository;
import com.appslab.selfstorage.services.ReportService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private ReportRepository reportRepository;
    private UserService userService;

    public ReportServiceImpl(ReportRepository reportRepository, UserService userService) {
        this.reportRepository = reportRepository;
        this.userService = userService;
    }

    @Override
    public void createReport(UploadedFile uploadedFile,String reason) {
        Report report = new Report();
        report.setCreatorId(userService.getSpecifyUserId());
        report.setReason(reason);
        report.setFileId(uploadedFile.getId());
        reportRepository.save(report);
    }

    @Override
    public Iterator<Report> getAllReports() {
        return reportRepository.findAll().iterator();
    }
}
