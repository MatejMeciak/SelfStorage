package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.models.UploadedFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepositoryDB extends CrudRepository<UploadedFile,Long> {
    @Query("SELECT u FROM UploadedFile u WHERE u.fileName LIKE %?1% AND u.ownerId = ?2")
    List<UploadedFile> findByFileName(String keyword, Long ownerId);

    @Query("SELECT u FROM UploadedFile u WHERE u.fileName LIKE %?1% AND u.access = ?2")
    List<UploadedFile> findByFileName(String keyword,Boolean access);

    List<UploadedFile> findByOwnerId(Long customUserId);

    List<UploadedFile> findByAccess(Boolean access);

    List<UploadedFile> findByFolderId(Long folderId);

    List<UploadedFile> findByOwner(CustomUser customUser);
}