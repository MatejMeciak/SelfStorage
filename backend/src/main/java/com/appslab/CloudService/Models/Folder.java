package com.appslab.CloudService.Models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String folderName;

    protected Long date;

    @OneToMany
    protected List<UploadedFile> uploadedFileList;

    protected Boolean access;

    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected CustomUser owner;

    @Column(name = "owner_id")
    protected Long ownerId;

    public Folder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Long getDate() {
        return date;
    }

    public void setDate() {
        this.date = Calendar.getInstance().getTime().getTime();
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long customUserId) {
        this.ownerId = customUserId;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public List<UploadedFile> getUploadedFileList() {
        return uploadedFileList;
    }

    public void setUploadedFileList(List<UploadedFile> uploadedFileList) {
        this.uploadedFileList = uploadedFileList;
    }
}
