package com.appslab.CloudService.Models;


import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;

public class User{

    @NotNull
    @NotEmpty
    protected String email;

    @NotNull
    @NotEmpty
    protected String password;

    @NotNull
    @NotEmpty
    protected String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
