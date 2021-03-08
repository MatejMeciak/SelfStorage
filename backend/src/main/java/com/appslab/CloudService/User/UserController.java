package com.appslab.CloudService.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    public void registration(@RequestBody User user){
        userService.registrationUser(user);
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody User user){
        userService.loginUser(user);
    }
}
