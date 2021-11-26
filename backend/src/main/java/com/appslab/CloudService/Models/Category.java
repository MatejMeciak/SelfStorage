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

    @ManyToOne
    private CustomUser creator;

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

    public void setAdminAccess(Boolean adminAccess) {
        this.adminAccess = adminAccess;
    }
}
