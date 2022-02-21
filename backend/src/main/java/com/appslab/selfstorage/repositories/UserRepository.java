package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser,Long> {
    Optional<CustomUser> findByUsername(String username);

    Boolean existsByUsername(String Username);

    CustomUser findByEmail(String email);

    Boolean existsByEmail(String email);

}