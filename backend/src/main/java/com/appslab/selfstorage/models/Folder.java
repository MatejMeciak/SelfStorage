package com.appslab.selfstorage.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @OneToMany(mappedBy = "folder")
    @JsonManagedReference
    protected List<File> fileList;

    protected Boolean access;

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "folder_category",joinColumns = @JoinColumn(name = "folder_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    protected List<Category> categories;

    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    protected User owner;

    @Column(name = "owner_id")
    protected Long ownerId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinTable(name = "share_folder",joinColumns = @JoinColumn(name = "folder_id"),inverseJoinColumns = @JoinColumn(name = "customUser_id"))
    protected List<User> friends;

    public Folder() {
    }

    public void setDate() {
        this.date = Calendar.getInstance().getTime().getTime();
    }


    public void setFriends(User friend) {
        this.friends.add(friend);
    }
}
