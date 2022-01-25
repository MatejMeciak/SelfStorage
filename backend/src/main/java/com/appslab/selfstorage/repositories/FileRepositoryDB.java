package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.models.UploadedFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepositoryDB extends CrudRepository<UploadedFile,Long> {
    List<UploadedFile> findByNameContainingAndOwnerId(String keyword, Long ownerId);

    List<UploadedFile> findByNameContainingAndAccess(String name,Boolean access);

    List<UploadedFile> findByOwnerId(Long customUserId);

    List<UploadedFile> findByAccess(Boolean access);

    List<UploadedFile> findByOwner(CustomUser customUser);
}