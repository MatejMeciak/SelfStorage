package com.appslab.selfstorage.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @JsonIgnore
    protected UUID uuid;

    protected Long fileSize;

    @NotEmpty
    protected String name;

    protected String mimeType;

    protected Long date;

    protected Boolean access=false;

    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    protected User owner;

    @Column(name = "owner_id")
    protected Long ownerId;

    @JoinColumn(name = "folder_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    protected Folder folder;

    @Column(name = "folder_id")
    protected Long folderId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "file_category",joinColumns = @JoinColumn(name = "uploadedFile_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonBackReference
    protected List<Category> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "share_files",joinColumns = @JoinColumn(name = "uploadedFile_id"),inverseJoinColumns = @JoinColumn(name = "customUser_id"))
    protected List<User> friends;

    @JsonIgnore
    @OneToMany(mappedBy = "file")
    private List<Report> reports;

    public File(){
    }

    public void setDate(){
        this.date = Calendar.getInstance().getTime().getTime();
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

    public void setFriends(User friend) {
        this.friends.add(friend);
    }
}