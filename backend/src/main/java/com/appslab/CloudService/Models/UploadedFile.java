package com.appslab.CloudService.Models;

import javax.persistence.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Entity
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected Long sizeFile;

    protected String nameFile;

    @Column(name = "hash", nullable = false, unique = true)
    protected String hash;

    protected String mimeType;

    protected Long date;

    public static final int RADIX = 16;

    public UploadedFile() {

    }

    public Long getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(Long sizeFile) {
        this.sizeFile = sizeFile;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash() throws NoSuchAlgorithmException {
        String transformed = this.nameFile + this.mimeType + new Date().getTime();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(transformed.getBytes(StandardCharsets.UTF_8));
        this.hash = new BigInteger(1, messageDigest.digest()).toString(RADIX);
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

    public void setDate() {
        this.date  = Calendar.getInstance().getTime().getTime();
    }

}

