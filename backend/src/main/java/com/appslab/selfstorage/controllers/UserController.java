package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.config.CurrentUser;
import com.appslab.selfstorage.dto.LocalUser;
import com.appslab.selfstorage.services.ReportService;
import com.appslab.selfstorage.services.UserService;
import com.appslab.selfstorage.util.GeneralUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class UserController {

    private UserService userService;
    private ReportService reportService;

    public UserController(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
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

    @GetMapping("/user")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok(ReportService.class.cast(reportService.getAllReports()));
    }
}
