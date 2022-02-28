package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.models.UploadedFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepositoryDB extends JpaRepository<File,Long> {
    List<File> findByNameContainingAndOwnerId(String keyword, Long ownerId);

    List<File> findByNameContainingAndAccess(String name, Boolean access);

    List<File> findByOwnerId(Long customUserId);

    List<File> findByAccess(Boolean access);

    List<File> findAllByFolderIdAndOwnerId(Long id, Long ownerId);

    List<File> findByFriends(Long id);

    List<File> findByFolderId(Long folderId);
}