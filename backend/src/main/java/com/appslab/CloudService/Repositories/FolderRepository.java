package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface FolderRepository extends CrudRepository<Folder,Long> {

    Optional<Folder> findByFolderName(String folderName);

    @Query("SELECT u FROM Folder u WHERE u.folderName LIKE %?1% AND u.ownerId = ?2")
    List<Folder> findByFolderName(String keyword, Long ownerId);

    List<Folder> findByOwnerId(Long userId);
}
