package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepositoryDB extends JpaRepository<UploadedFile,Long> {
    List<UploadedFile> findByNameContainingAndOwnerId(String keyword, Long ownerId);

    List<UploadedFile> findByNameContainingAndAccess(String name,Boolean access);

    List<UploadedFile> findByOwnerId(Long customUserId);

    List<UploadedFile> findByAccess(Boolean access);

    List<UploadedFile> findAllByFolderIdAndOwnerId(Long id, Long ownerId);

    List<UploadedFile> findByFriends(Long id);

    List<UploadedFile> findByFolderId(Long folderId);
}