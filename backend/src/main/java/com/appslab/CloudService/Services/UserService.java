package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.CustomUser;
import com.appslab.CloudService.DTO.RegistrationRequestDTO;

public interface UserService {
    void createUser(RegistrationRequestDTO registrationRequestDTO);

    Boolean userAlreadyExists(RegistrationRequestDTO registrationRequestDTO);

    Long getSpecifyUserId();

    void changePassword(String password);

    CustomUser getUser();
}