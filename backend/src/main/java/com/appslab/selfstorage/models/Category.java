package com.appslab.selfstorage.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @JoinColumn(name = "creator_id",updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User creator;

    @Column(name = "creator_id")
    private Long creatorId;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    @JsonBackReference
    private List<File> files;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    @JsonManagedReference
    private List<Folder> folders;

    public Category() {
    }
}
