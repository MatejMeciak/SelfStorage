package com.appslab.CloudService.User;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
