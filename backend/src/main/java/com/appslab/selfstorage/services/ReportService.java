package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.UploadedFile;

import java.util.Iterator;
import java.util.List;

public interface ReportService{
    Report createReport(UploadedFile uploadedFile, String reason);

    List<Report> getAllReports();

    Report removeReport(Report report);

    UploadedFile submitReport(Long id);

    Report getCurrentReport(Long id);
}
