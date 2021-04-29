package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface UploadFileService {
    void deleteFile(Long id);
    List<UploadedFile> listOfFiles(Long customUserId);
    Path getDocStorageLocation();
    UploadedFile uploadedFile(MultipartFile multipartFile);
    void saveUploadedFileToDB(UploadedFile uploadedFile);
    Optional<UploadedFile> findFileById(Long fileID);
    Path pathToSpecificFile(UploadedFile uploadedFile);
    void savingFileToStorage(UploadedFile uploadedFile, MultipartFile multipartFile) throws Exception;
    List<UploadedFile> findSearchingFiles(String searchingFiles, Long customUserId);
    void saveEditFile(UploadedFile uploadedFile);
}