package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.dto.LocalUser;
import com.appslab.selfstorage.dto.SignUpRequest;
import com.appslab.selfstorage.dto.SocialProvider;
import com.appslab.selfstorage.exception.OAuth2AuthenticationProcessingException;
import com.appslab.selfstorage.exception.UserAlreadyExistAuthenticationException;
import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.User;
import com.appslab.selfstorage.models.Role;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.repositories.CategoryRepository;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.RoleRepository;
import com.appslab.selfstorage.repositories.UserRepository;
import com.appslab.selfstorage.security.oauth2.user.OAuth2UserInfo;
import com.appslab.selfstorage.security.oauth2.user.OAuth2UserInfoFactory;
import com.appslab.selfstorage.services.UserService;

import com.appslab.selfstorage.util.GeneralUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private FileRepositoryDB fileRepositoryDB;
    private CategoryRepository categoryRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, FileRepositoryDB fileRepositoryDB, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.fileRepositoryDB = fileRepositoryDB;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String changePassword(String oldPassword, String newPassword) {
        User user = userRepository.findById(getSpecifyUserId()).get();
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "success";
        }
        return "failed";
    }

    @Override
    @Transactional(value = "transactionManager")
    public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
        if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
            throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
        }
        User user = buildUser(signUpRequest);
        Date now = Calendar.getInstance().getTime();
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        user.setSpaceSize(2000000000L);

        userRepository.save(user);
        userRepository.flush();

        Category category = new Category();
        category.setName("Favourite");
        category.setCreatorId(user.getId());
        categoryRepository.save(category);

        return user;
    }

    private User buildUser(final SignUpRequest formDTO) {
        User user = new User();
        user.setUsername(formDTO.getUsername());
        user.setEmail(formDTO.getEmail());
        if(formDTO.getPassword() != null)
        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));

        final HashSet<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(Role.ROLE_USER));
        user.setRoles(roles);
        user.setProvider(formDTO.getSocialProvider().getProviderType());
        user.setEnabled(true);
        user.setProviderUserId(formDTO.getProviderUserId());

        return user;
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
            throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
        } else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        User user = findUserByEmail(oAuth2UserInfo.getEmail());
        if (user != null) {
            if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
                throw new OAuth2AuthenticationProcessingException(
                        "Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userDetails);
        }

        return LocalUser.create(user, attributes, idToken, userInfo);
    }

    @Override
    public Long usedSpaceOfStorage() {
        List<Long> sizesOfFiles = fileRepositoryDB.findByOwnerId(getSpecifyUserId()).stream().map(File::getFileSize).collect(Collectors.toList());
        Long usedSpace = 0L;
        for(int i = 0; i<fileRepositoryDB.findByOwnerId(getSpecifyUserId()).stream().map(File::getFileSize).count(); i++ ) {
            usedSpace += sizesOfFiles.get(i);
        }
        return usedSpace;
    }

    @Override
    public Long settingSizeOfSpace(Long sizeSpace, Long userId) {
        User user = userRepository.findById(userId).get();
        if(sizeSpace!=null){
            user.setSpaceSize(sizeSpace);
            userRepository.save(user);
        }
        return sizeSpace;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.remove(getUser());
        return users;
    }

    @Override
    public List<User> getFriends() {
        return this.fileRepositoryDB.findByOwnerId(getSpecifyUserId()).stream()
                .flatMap(file -> file.getFriends().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public User changeUsername(String username) {
        User user = getUser();
        user.setUsername(username);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }

    private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addUsernameName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
                .addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword(String.valueOf(System.nanoTime())).build();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public User getUser() {
        return userRepository.findById(getSpecifyUserId()).get();
    }

    @Override
    public Long getSpecifyUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        return user.getId();
    }
}
