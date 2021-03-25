package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface UploadFileService {
    void saveFile(UploadedFile file);
    void deleteFile(Long id);
    List<UploadedFile> listOfFiles();
    Path getDocStorageLocation();
    UploadedFile uploadedFile(MultipartFile multipartFile);
    void saveUploadedFileToDB(UploadedFile uploadedFile);
    Optional<UploadedFile> findFileById(Long fileID);
}
