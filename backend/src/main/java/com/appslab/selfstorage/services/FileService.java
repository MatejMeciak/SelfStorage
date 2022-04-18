package com.appslab.selfstorage.services;

import com.appslab.selfstorage.dto.FileBasicInfo;
import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.models.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

public interface FileService {
    File uploadedFile(MultipartFile multipartFile, Boolean access);

    FileBasicInfo deleteFile(Long id) throws Exception;

    List<File> getListOfMyFiles();

    void saveUploadedFileToDB(File file);

    Optional<File> findFileById(Long fileID);

    void saveFileToStorage(File file, MultipartFile multipartFile) throws Exception;

    List<File> findSearchFiles(String searchFiles);

    File saveEditFile(FileBasicInfo fileBasicInfo);

    List<File> findSearchFilesInPublicList(String keyword, Boolean access);

    ResponseEntity<InputStreamResource> getFile(File file) throws Exception;

    ResponseEntity<InputStreamResource> getFileForAdmin(Long id) throws Exception;

    void shareFileWithFriends(String email, Long id);

    List<File> getMySharedFiles();

    List<File> getSharedFilesFromOtherUsers();

    List<File> getPublicFiles();

    ResponseEntity<InputStreamResource> getPublicFile(Long id) throws Exception;

    List<File> getFiles();

    List<Category> categoriesFromFile(Long id);

    List<File> getMySharedFilesWithCurrentUser(String email);

    List<File> getSharedFilesFromSpecificFriend(String email);

    User updateProfilePicture(MultipartFile multipartFile) throws Exception;

    ResponseEntity<InputStreamResource> getProfilePicture() throws Exception;

    User deleteProfilePicture() throws Exception;
}