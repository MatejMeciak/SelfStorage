package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.UploadedFile;

import java.util.List;

public interface ReportService{
    Report createReport(Long fileId, String reason);

    List<Report> getAllReports();

    Report removeReport(Long id);

    UploadedFile submitReport(Long id);

    Report getCurrentReport(Long id);
}
