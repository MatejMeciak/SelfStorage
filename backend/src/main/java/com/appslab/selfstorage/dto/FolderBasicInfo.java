package com.appslab.selfstorage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderBasicInfo {
    private Long id;
    private String name;
    private boolean access;
    private Long date;
}
