package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.dto.RegistrationRequestDTO;
import com.appslab.selfstorage.repositories.UserRepository;
import com.appslab.selfstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(RegistrationRequestDTO registrationRequest){
        CustomUser customUser = new CustomUser(passwordEncoder.encode(registrationRequest.getPassword()), registrationRequest.getUsername(), registrationRequest.getFirstName(), registrationRequest.getLastName());
        userRepository.save(customUser);
    }

    @Override
    public Boolean userAlreadyExists(RegistrationRequestDTO registrationRequest) {
        Boolean username = userRepository.existsByUsername(registrationRequest.getUsername());
        if (username != true){
            createUser(registrationRequest);
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Long getSpecifyUserId(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<CustomUser> user = userRepository.findByUsername(username);
        return user.get().getId();
    }

    @Override
    public void changePassword(String password) {
        CustomUser customUser = userRepository.findById(getSpecifyUserId()).get();
        customUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(customUser);
    }

    @Override
    public CustomUser getUser() {
        return this.userRepository.findById(getSpecifyUserId()).get();
    }
}