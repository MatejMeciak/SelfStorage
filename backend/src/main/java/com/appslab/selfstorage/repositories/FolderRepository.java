package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.Folder;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface FolderRepository extends CrudRepository<Folder,Long> {

    List<Folder> findByNameContainingAndOwnerId(String name, Long ownerId);

    List<Folder> findByOwnerId(Long userId);

    List<Folder> findByAccess(Boolean access);
}
