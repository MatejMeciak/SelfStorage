package com.appslab.selfstorage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Getter
@Setter
public class CustomUser implements UserDetails {

    private static final long serialVersionUID = 65981149772133526L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    private String providerUserId;

    @NotBlank
    private String email;

    @Column(columnDefinition = "BIT", length = 1)
    private boolean enabled;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    private String provider;

    private Long spaceSize;

    @JsonIgnore
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String username;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "USER_ID")},inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;

    @OneToMany
    private List<Category> categories;

    @OneToMany
    private List<UploadedFile> uploadedFiles;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<UploadedFile> sharedFiles;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Folder> sharedFolder;

    @OneToMany
    private List<Report> reports;

    public CustomUser() {
    }

    public CustomUser(@NotEmpty String password, @NotEmpty String username) {
        this.password = password;
        this.username = username;
    }

    public void setSharedFiles(List<UploadedFile> uploadedFiles1) {
        this.sharedFiles = uploadedFiles1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true ;
    }
}