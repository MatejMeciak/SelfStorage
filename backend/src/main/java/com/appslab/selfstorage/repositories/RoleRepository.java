package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
