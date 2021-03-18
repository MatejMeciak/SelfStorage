package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.User;

public interface UserService {
    void registrationUser(User user);
    void loginUser(User user);
}
