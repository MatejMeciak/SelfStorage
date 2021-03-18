package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
