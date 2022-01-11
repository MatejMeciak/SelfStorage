package com.appslab.selfstorage.config;

import com.appslab.selfstorage.dto.SocialProvider;
import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.models.Role;
import com.appslab.selfstorage.repositories.RoleRepository;
import com.appslab.selfstorage.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Component
public class SetupDataLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args){
        Role userRole = createRoleIfNotFound(Role.ROLE_USER);
        Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
        createUserIfNotFound("admin@javachinna.com", Set.of(userRole, adminRole));
    }

    @Transactional
    private final CustomUser createUserIfNotFound(final String email, Set<Role> roles) {
        CustomUser user = userRepository.findByEmail(email);
        if (user == null) {
            user = new CustomUser();
            user.setUsername("Admin");
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("admin@"));
            user.setRoles(roles);
            user.setProvider(SocialProvider.LOCAL.getProviderType());
            user.setEnabled(true);
            Date now = Calendar.getInstance().getTime();
            user.setCreatedDate(now);
            user.setModifiedDate(now);
            user.setFirstName("Firstname");
            user.setLastName("Lastname");
            user = userRepository.save(user);
        }
        return user;
    }

    @Transactional
    private final Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(new Role(name));
        }
        return role;
    }
}
