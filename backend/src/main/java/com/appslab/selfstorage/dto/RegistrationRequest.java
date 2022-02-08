package com.appslab.selfstorage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    protected String password;
    protected String username;
}
