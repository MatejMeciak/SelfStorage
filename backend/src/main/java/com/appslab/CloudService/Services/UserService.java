package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.User;

public interface UserService {
    void getRegistrationUser(User user);
    void loginUser(User user);
}
