package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.ReportRepository;
import com.appslab.selfstorage.services.ReportService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private ReportRepository reportRepository;
    private FileRepositoryDB fileRepositoryDB;
    private UserService userService;

    public ReportServiceImpl(ReportRepository reportRepository,FileRepositoryDB fileRepositoryDB, UserService userService) {
        this.reportRepository = reportRepository;
        this.userService = userService;
        this.fileRepositoryDB = fileRepositoryDB;
    }

    @Override
    public Report createReport(UploadedFile uploadedFile,String reason) {
        Report report = new Report();
        report.setCreatorId(userService.getSpecifyUserId());
        report.setReason(reason);
        report.setFileId(uploadedFile.getId());
        return reportRepository.save(report);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Report removeReport(Long id) {
        Report report = reportRepository.findById(id).get();
        Long fileId = report.getFileId();
        reportRepository.deleteAllByFileId(fileId);
        return report;
    }

    @Override
    public UploadedFile submitReport(Long id) {
        Report report = reportRepository.findById(id).get();
        UploadedFile uploadedFile = fileRepositoryDB.findById(report.getFileId()).get();
        uploadedFile.setAccess(false);
        reportRepository.deleteAllByFileId(report.getFileId());
        return fileRepositoryDB.save(uploadedFile);
    }

    @Override
    public Report getCurrentReport(Long id) {
        return reportRepository.findById(id).get();
    }
}
