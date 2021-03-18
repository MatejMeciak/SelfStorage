package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.Models.User;
import com.appslab.CloudService.Repositories.UserRepository;
import com.appslab.CloudService.Services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registrationUser(User user){
        userRepository.save(user);
    }

    @Override
    public void loginUser(User user){
        userRepository.findById(user.getId());
    }
}
