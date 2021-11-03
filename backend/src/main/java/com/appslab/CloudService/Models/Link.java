package com.appslab.CloudService.Models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String linkName;

    private String link;

    private Long date;

    private Boolean access=false;

    @JoinColumn(name = "customUser_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private CustomUser customUser;

    @Column(name = "customUser_id")
    private Long customUserId;

    @JoinColumn(name = "folder_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Folder folder;

    @Column(name = "folder_id")
    private Long folderId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_links",joinColumns = @JoinColumn(name = "link_id"),inverseJoinColumns = @JoinColumn(name = "customUser_id"))
    private List<CustomUser> friends;

    public Link() {
    }

    public Long getId() {
        return id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = Calendar.getInstance().getTime().getTime();
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public Long getCustomUserId() {
        return customUserId;
    }

    public void setCustomUserId(Long customUserId) {
        this.customUserId = customUserId;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
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
