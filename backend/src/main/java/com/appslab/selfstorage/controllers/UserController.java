package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.config.CurrentUser;
import com.appslab.selfstorage.dto.LocalUser;
import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.dto.RegistrationRequestDto;
import com.appslab.selfstorage.services.UserService;
import com.appslab.selfstorage.util.GeneralUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/user")
//    public CustomUser getUser() {
//        return this.userService.getUser();
//    }
//
//    @PostMapping("/registration")
//    public RegistrationRequestDto registration(@RequestBody RegistrationRequestDto registrationRequestDTO){
//        if(userService.userAlreadyExists(registrationRequestDTO)!=true){
//            return registrationRequestDTO;
//        }
//        else {
//            return null;
//        }
//    }
//
//    @PutMapping("/changePassword")
//    public void changePassword(@RequestParam String password){
//        userService.changePassword(password);
//    }

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }
}
