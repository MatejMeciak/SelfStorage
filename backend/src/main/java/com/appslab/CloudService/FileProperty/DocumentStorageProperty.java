package com.appslab.CloudService.FileProperty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("document")
public class DocumentStorageProperty {
    private String uploadDirectory;

    public String getUploadDirectory(){
        return uploadDirectory;
    }

    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }
}
