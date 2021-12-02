package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.dto.RegistrationRequestDTO;

public interface UserService {
    void createUser(RegistrationRequestDTO registrationRequestDTO);

    Boolean userAlreadyExists(RegistrationRequestDTO registrationRequestDTO);

    Long getSpecifyUserId();

    void changePassword(String password);

    CustomUser getUser();
}