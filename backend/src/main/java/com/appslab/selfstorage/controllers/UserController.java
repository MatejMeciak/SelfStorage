package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.config.CurrentUser;
import com.appslab.selfstorage.dto.LocalUser;
import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.services.ReportService;
import com.appslab.selfstorage.services.UserService;
import com.appslab.selfstorage.util.GeneralUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {

    private UserService userService;
    private ReportService reportService;

    public UserController(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        userService.changePassword(oldPassword, newPassword);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @GetMapping("/storageSpace")
    public List<Long> getStorageSpace(){
        return List.of(userService.usedSpaceOfStorage(),userService.getUser().getSpaceSize());
    }

    @GetMapping("/listUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CustomUser> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/settingSpace")
    @PreAuthorize("hasRole('ADMIN')")
    public Long settingSpace(@RequestParam Long sizeSpace, @RequestParam Long userId){
        return userService.settingSizeOfSpace(sizeSpace, userId);
    }
}
