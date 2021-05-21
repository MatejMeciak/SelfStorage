package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.CustomUser;
import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<CustomUser,Long> {
    Optional<CustomUser> findByUsername(String username);

    Boolean existsByUsername(String Username);

    Boolean existsByUploadedFiles1(UploadedFile uploadedFile);
}