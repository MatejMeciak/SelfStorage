package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.ReportRepository;
import com.appslab.selfstorage.services.ReportService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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
    public Report createReport(Long fileId,String reason) {
        Report report = new Report();
        report.setCreatorId(userService.getSpecifyUserId());
        report.setReason(reason);
        report.setCreatedDate(Calendar.getInstance().getTime().getTime());
        report.setFileId(fileRepositoryDB.findById(fileId).get().getId());
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
        reportRepository.delete(report);
        return report;
    }

    @Override
    @Transactional
    public File submitReport(Long id) {
        Report report = reportRepository.findById(id).get();
        File file = fileRepositoryDB.findById(report.getFileId()).get();
        file.setAccess(false);
        fileRepositoryDB.save(file);
        reportRepository.deleteReportsByFileId(file.getId());
        return file;
    }

    @Override
    public Report getCurrentReport(Long id) {
        return reportRepository.findById(id).get();
    }
}
