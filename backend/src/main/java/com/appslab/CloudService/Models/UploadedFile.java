package com.appslab.CloudService.Models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected UUID uuid;

    protected Long fileSize;

    protected String fileName;

    protected String mimeType;

    protected Long date;

    protected Boolean access=false;

    @JoinColumn(name = "customUser_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected CustomUser owner;

    @Column(name = "customUser_id")
    protected Long ownerId;

    @JoinColumn(name = "folder_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected Folder folder;

    @Column(name = "folder_id")
    protected Long folderId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "share_files",joinColumns = @JoinColumn(name = "uploadedFile_id"),inverseJoinColumns = @JoinColumn(name = "customUser_id"))
    protected List<CustomUser> friends;

    public UploadedFile(){

    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public List<CustomUser> getFriends() {
        return friends;
    }

    public void setFriends(CustomUser friends) {
        this.friends.add(friends);
    }
}