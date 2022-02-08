package com.appslab.selfstorage.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotEmpty
    protected String name;

    protected Long date;

    @OneToMany
    protected List<UploadedFile> uploadedFileList;

    protected Boolean access;

    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected Category category;

    @Column(name = "category_id")
    protected Long categoryId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
