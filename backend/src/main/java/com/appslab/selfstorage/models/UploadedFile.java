package com.appslab.selfstorage.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected UUID uuid;

    protected Long fileSize;

    @NotEmpty
    protected String name;

    protected String mimeType;

    protected Long date;

    protected Boolean access=false;

    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected CustomUser owner;

    @Column(name = "owner_id")
    protected Long ownerId;

    @JoinColumn(name = "folder_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected Folder folder;

    @Column(name = "folder_id")
    protected Long folderId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "file_category",joinColumns = @JoinColumn(name = "uploadedFile_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonManagedReference
    protected List<Category> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "share_files",joinColumns = @JoinColumn(name = "uploadedFile_id"),inverseJoinColumns = @JoinColumn(name = "customUser_id"))
    protected List<CustomUser> friends;

    @OneToMany
    private List<Report> reports;

    public UploadedFile(){
    }

    public void setDate(){
        this.date = Calendar.getInstance().getTime().getTime();
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

    public void setFriends(CustomUser friend) {
        this.friends.add(friend);
    }
}