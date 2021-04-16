package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepositoryDB extends CrudRepository<UploadedFile,Long> {
    @Query("SELECT u FROM UploadedFile u WHERE u.fileName LIKE %?1%")
    List<UploadedFile> findByFileName(String keyword);
}
