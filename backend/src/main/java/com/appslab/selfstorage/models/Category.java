package com.appslab.selfstorage.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String name;

    @JoinColumn(name = "creator_id",updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private CustomUser creator;

    @Column(name = "creator_id")
    private Long creatorId;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<UploadedFile> files;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Folder> folders;

    public Category() {
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public CustomUser getCreator() {
//        return creator;
//    }
//
//    public void setCreator(CustomUser creator) {
//        this.creator = creator;
//    }
//
//    public List<UploadedFile> getFiles() {
//        return files;
//    }
//
//    public void setFiles(List<UploadedFile> files) {
//        this.files = files;
//    }
//
//    public List<Folder> getFolders() {
//        return folders;
//    }
//
//    public void setFolders(List<Folder> folders) {
//        this.folders = folders;
//    }
//
//    public Long getCreatorId() {
//        return creatorId;
//    }
//
//    public void setCreatorId(Long creatorId) {
//        this.creatorId = creatorId;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
}
