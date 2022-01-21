package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.Report;
import com.appslab.selfstorage.models.UploadedFile;

import java.util.Iterator;

public interface ReportService{
    void createReport(UploadedFile uploadedFile, String reason);

    Iterator<Report> getAllReports();
}
