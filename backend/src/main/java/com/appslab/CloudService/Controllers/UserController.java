package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Models.CustomUser;
import com.appslab.CloudService.Models.RegistrationRequest;
import com.appslab.CloudService.Services.UserService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public CustomUser getUser() {
        return this.userService.getUser();
    }

    @PostMapping("/registration")
    public RegistrationRequest registration(@RequestBody RegistrationRequest registrationRequest){
        if(userService.userAlreadyExists(registrationRequest)!=true){
            return registrationRequest;
        }
        else {
            return null;
        }
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestParam String password){
        userService.changePassword(password);
    }
}
