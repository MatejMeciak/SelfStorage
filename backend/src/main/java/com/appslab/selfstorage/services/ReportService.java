package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.UploadedFile;

public interface ReportService{
    void createReport(UploadedFile uploadedFile, String reason);
}
