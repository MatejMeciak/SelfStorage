package com.appslab.selfstorage.dto;

import com.appslab.selfstorage.models.User;
import com.appslab.selfstorage.models.File;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailedReport {
    private File file;
    private User creator;
    private String reason;
    private Long date;
}
