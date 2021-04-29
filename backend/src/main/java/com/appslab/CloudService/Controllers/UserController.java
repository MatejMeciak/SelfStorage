package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Models.RegistrationRequest;
import com.appslab.CloudService.Services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registration(@RequestBody RegistrationRequest registrationRequest){
        if(userService.userAlreadyExists(registrationRequest)!=true){
            return "registration is succesfully";
        }
        else {
            return "user with this username already exists";
        }
    }
}
