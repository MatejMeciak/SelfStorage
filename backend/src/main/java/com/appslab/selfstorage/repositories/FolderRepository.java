package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.UploadedFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface FolderRepository extends CrudRepository<Folder,Long> {

    List<Folder> findByNameContainingAndOwnerId(String name, Long ownerId);

    List<Folder> findByOwnerId(Long userId);

    List<Folder> findByAccess(Boolean access);
}
