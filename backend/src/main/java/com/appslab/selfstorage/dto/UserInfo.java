package com.appslab.selfstorage.dto;

import lombok.Value;

import java.util.List;

@Value
public class UserInfo {
    private String id, username, email, provider;
    private List<String> roles;
}

