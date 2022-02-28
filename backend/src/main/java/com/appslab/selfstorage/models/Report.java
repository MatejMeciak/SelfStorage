package com.appslab.selfstorage.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "file_id",insertable = false,updatable = false)
    private File file;

    @Column(name = "file_id")
    private Long fileId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "creator_id",insertable = false,updatable = false)
    private User creator;

    @Column(name = "creator_id")
    private Long creatorId;

    private Long createdDate;

    public Report() {
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = Calendar.getInstance().getTime().getTime();
    }
}

