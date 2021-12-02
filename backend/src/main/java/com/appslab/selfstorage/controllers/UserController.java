package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.dto.RegistrationRequestDTO;
import com.appslab.selfstorage.services.UserService;
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
    public RegistrationRequestDTO registration(@RequestBody RegistrationRequestDTO registrationRequestDTO){
        if(userService.userAlreadyExists(registrationRequestDTO)!=true){
            return registrationRequestDTO;
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
