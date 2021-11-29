package com.appslab.CloudService.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Boolean adminAccess;

    @JoinColumn(name = "creator_id",updatable = false,insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private CustomUser creator;

    @Column(name = "creator_id")
    private Long creatorId;

    @OneToMany
    private List<UploadedFile> files;

    @OneToMany
    private List<Folder> folders;

    @OneToMany
    private List<Link> links;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdminAccess() {
        return adminAccess;
    }

    public CustomUser getCreator() {
        return creator;
    }

    public void setCreator(CustomUser creator) {
        this.creator = creator;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void setAdminAccess(Boolean adminAccess) {
        this.adminAccess = adminAccess;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

}
