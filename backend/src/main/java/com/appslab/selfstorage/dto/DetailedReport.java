package com.appslab.selfstorage.dto;

import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.models.UploadedFile;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailedReport {
    private UploadedFile uploadedFile;
    private CustomUser creator;
    private String reason;
    private Long date;
}
