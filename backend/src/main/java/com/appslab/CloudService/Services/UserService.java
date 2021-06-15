package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.CustomUser;
import com.appslab.CloudService.Models.RegistrationRequest;

public interface UserService {
    void createUser(RegistrationRequest registrationRequest);

    Boolean userAlreadyExists(RegistrationRequest registrationRequest);

    Long getSpecifyUserId();

    void changePassword(String password);

    CustomUser getUser();
}