package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.CustomUser;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<CustomUser,Long> {
    Optional<CustomUser> findByUsername(String username);

    Boolean existsByUsername(String Username);

}