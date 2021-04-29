package com.appslab.CloudService.Models;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected UUID uuid;

    protected Long sizeFile;

    protected String fileName;

    protected String mimeType;

    protected Long date;

    @JoinColumn(name = "customUser_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected CustomUser customUser;

    @Column(name = "customUser_id")
    protected Long customUserId;

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
        this.date = Calendar.getInstance().getTime().getTime();
    }

    public Long getCustomUserId() {
        return customUserId;
    }

    public void setCustomUserId(Long customUserId) {
        this.customUserId = customUserId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }
}