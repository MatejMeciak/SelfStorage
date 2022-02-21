package com.appslab.selfstorage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileBasicInfo {
    private String name;
    private Long fileSize;
    private Long date;
}
