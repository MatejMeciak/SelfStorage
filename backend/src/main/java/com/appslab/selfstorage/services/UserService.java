package com.appslab.selfstorage.services;

import com.appslab.selfstorage.dto.LocalUser;
import com.appslab.selfstorage.dto.SignUpRequest;
import com.appslab.selfstorage.exception.UserAlreadyExistAuthenticationException;
import com.appslab.selfstorage.models.CustomUser;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    //void createUser(RegistrationRequestDto registrationRequestDTO);

    //Boolean userAlreadyExists(RegistrationRequestDto registrationRequestDTO);

    CustomUser getUser();

    Long getSpecifyUserId();

    String changePassword(String oldPassword, String newPassword);

    CustomUser registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    CustomUser findUserByEmail(String email);

    Optional<CustomUser> findUserById(Long id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

    Long usedSpaceOfStorage();

    Long settingSizeOfSpace(Long sizeSpace, Long userId);

    List<CustomUser> getAllUsers();

    List<CustomUser> getFriends();
}