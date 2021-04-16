package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.Models.User;
//import com.appslab.CloudService.Repositories.UserRepository;
import com.appslab.CloudService.Services.UserService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

//@Service
//public class UserServiceImpl implements UserService, UserDetailsService {
//    UserRepository userRepository;
//
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void getRegistrationUser(User user){
//        userRepository.save(user);
//    }
//
//    @Override
//    public void loginUser(User user){
//        userRepository.findById(user.getId());
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        final Optional<User> optionalUser = userRepository.findByEmail(email);
//        if(optionalUser.isPresent()){
//            return optionalUser.get();
//        }
//        else{
//            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
//        }
//    }
//}
