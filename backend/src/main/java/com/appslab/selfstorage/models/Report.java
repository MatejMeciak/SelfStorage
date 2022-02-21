package com.appslab.selfstorage.models;

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
    @JoinColumn(name = "file_id",insertable = false,updatable = false)
    private UploadedFile uploadedFile;

    @Column(name = "file_id")
    private Long fileId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id",insertable = false,updatable = false)
    private CustomUser creator;

    @Column(name = "creator_id")
    private Long creatorId;

    private Long createdDate;

    public Report() {
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = Calendar.getInstance().getTime().getTime();
    }
}

