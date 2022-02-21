package com.appslab.selfstorage.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

@Entity
@Getter
@Setter
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "folder_category",joinColumns = @JoinColumn(name = "folder_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonManagedReference
    protected List<Category> categories;

    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    protected CustomUser owner;

    @Column(name = "owner_id")
    protected Long ownerId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "share_folder",joinColumns = @JoinColumn(name = "folder_id"),inverseJoinColumns = @JoinColumn(name = "customUser_id"))
    protected List<CustomUser> friends;

    public Folder() {
    }

    public void setDate() {
        this.date = Calendar.getInstance().getTime().getTime();
    }

    public void setFriends(CustomUser friend) {
        this.friends.add(friend);
    }
}
