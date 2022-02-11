package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.UploadedFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface UploadFileService {
    UploadedFile deleteFile(Long id) throws Exception;

    List<UploadedFile> getListOfMyFiles();

    UploadedFile uploadedFile(MultipartFile multipartFile,Boolean access);

    void saveUploadedFileToDB(UploadedFile uploadedFile);

    Optional<UploadedFile> findFileById(Long fileID);

    Path pathToSpecificFile(UploadedFile uploadedFile);

    void saveFileToStorage(UploadedFile uploadedFile, MultipartFile multipartFile) throws Exception;

    List<UploadedFile> findSearchFiles(String searchFiles);

    UploadedFile saveEditFile(UploadedFile uploadedFile);

    List<UploadedFile> findSearchFilesInPublicList(String keyword,Boolean access);

    ResponseEntity<InputStreamResource> getFile(UploadedFile uploadedFile) throws Exception;

    void shareFileWithFriends(String email, Long id);

    List<UploadedFile> getMySharedFiles();

    List<UploadedFile> getSharedFilesFromOtherUsers();

    List<UploadedFile> getPublicFiles();

    List<UploadedFile> getFiles();
}