package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface UploadFileService {
    void deleteFile(Long id);

    List<UploadedFile> listOfFiles(Long customUserId);

    Path getDocStorageLocation();

    UploadedFile uploadedFile(MultipartFile multipartFile,Boolean access);

    void saveUploadedFileToDB(UploadedFile uploadedFile);

    Optional<UploadedFile> findFileById(Long fileID);

    Path pathToSpecificFile(UploadedFile uploadedFile);

    void saveFileToStorage(UploadedFile uploadedFile, MultipartFile multipartFile) throws Exception;

    List<UploadedFile> findSearchFiles(String searchFiles, Long customUserId);

    void saveEditFile(UploadedFile uploadedFile);

    List<UploadedFile> findSearchFilesInPublicList(String keyword,Boolean access);

    ResponseEntity<InputStreamResource> getFile(UploadedFile uploadedFile) throws Exception;

    void saveEditFileWithUser(String username, UploadedFile uploadedFile);

    Object returnUploadedFileOrLink(UploadedFile uploadedFile);

    List<UploadedFile> returnShareFiles();
}