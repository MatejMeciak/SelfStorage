package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Services.UserService;
import com.appslab.CloudService.Models.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/registration")
    public String registration(WebRequest webRequest, Model model){
        User user = new User();
        model.addAttribute(user);
        return "Registration";
    }
}
