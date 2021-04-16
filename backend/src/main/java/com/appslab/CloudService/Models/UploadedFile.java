package com.appslab.CloudService.Models;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected Long sizeFile;

    protected String fileName;

    @Column(name = "originalFileName", nullable = false, unique = true)
    protected String originalFileName;

    protected String mimeType;

    protected Long date;

    public UploadedFile() {

    }

    public Long getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(Long sizeFile) {
        this.sizeFile = sizeFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName() throws NoSuchAlgorithmException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        Long nanoSeconds = System.nanoTime();
        String date = dateFormat.format(now);
        this.originalFileName = this.fileName +"_"+date+nanoSeconds;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(){
        this.date  = Calendar.getInstance().getTime().getTime();
    }

}

