package com.appslab.selfstorage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPasswords {
    private String oldPassword;
    private String newPassword;
}
