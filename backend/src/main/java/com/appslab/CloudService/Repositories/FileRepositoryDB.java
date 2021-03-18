package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.data.repository.CrudRepository;

public interface FileRepositoryDB extends CrudRepository<UploadedFile,Long> {
}
