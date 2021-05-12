package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.Folder;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FolderRepository extends CrudRepository<Folder,Long> {
    Optional<Folder> findByFolderName(String folderName);
}
