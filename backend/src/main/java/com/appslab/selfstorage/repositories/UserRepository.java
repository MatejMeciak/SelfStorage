package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String Username);

    User findByEmail(String email);

    Boolean existsByEmail(String email);

}