package com.appslab.CloudService;

import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<UploadedFile,Long> {
}
