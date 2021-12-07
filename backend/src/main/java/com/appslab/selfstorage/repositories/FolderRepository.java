package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface FolderRepository extends CrudRepository<Folder,Long> {

    Optional<Folder> findByFolderName(String folderName);

    List<Folder> findByFolderNameContainingAndOwnerId(String folderName, Long ownerId);

    List<Folder> findByOwnerId(Long userId);
}
