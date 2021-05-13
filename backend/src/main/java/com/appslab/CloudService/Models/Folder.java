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

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected CustomUser customUser;

    @Column(name = "user_id")
    protected Long customUserId;

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

    public Long getCustomUserId() {
        return customUserId;
    }

    public void setCustomUserId(Long customUserId) {
        this.customUserId = customUserId;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }
}
