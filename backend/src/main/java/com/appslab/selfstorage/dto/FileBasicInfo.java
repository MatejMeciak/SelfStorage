package com.appslab.selfstorage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileBasicInfo {
    private Long id;
    private String name;
    private Long fileSize;
    private Long date;
    private String mimeType;
    private boolean access;
    //folderId
}
