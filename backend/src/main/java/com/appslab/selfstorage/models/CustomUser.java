package com.appslab.selfstorage.models;


import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class CustomUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @NotEmpty
    protected String password;

    @NotNull
    @NotEmpty
    protected String username;

    @NotNull
    @NotEmpty
    protected String firstName;

    @NotNull
    @NotEmpty
    protected String lastName;

    @OneToMany
    protected List<UploadedFile> uploadedFiles;

    @ManyToMany(cascade = CascadeType.ALL)
    protected List<UploadedFile> sharedFiles;

    public CustomUser() {
    }

    public CustomUser(@NotEmpty String password, @NotEmpty String username, @NotEmpty String firstName, @NotEmpty String lastName) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UploadedFile> getSharedFiles() {
        return sharedFiles;
    }

    public void setSharedFiles(List<UploadedFile> uploadedFiles1) {
        this.sharedFiles = uploadedFiles1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }
}