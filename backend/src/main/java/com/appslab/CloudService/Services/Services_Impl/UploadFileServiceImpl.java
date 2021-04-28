package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.FileProperty.DocumentStorageProperty;
import com.appslab.CloudService.Models.UploadedFile;
import com.appslab.CloudService.Repositories.FileRepositoryDB;
import com.appslab.CloudService.Services.UploadFileService;
import com.appslab.CloudService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    private FileRepositoryDB fileRepositoryDB;
    private Path docStorageLocation;
    private UserService userService;

    @Autowired
    public UploadFileServiceImpl(FileRepositoryDB fileRepositoryDB, DocumentStorageProperty documentStorageProperty, UserService userService) throws Exception {
        this.fileRepositoryDB = fileRepositoryDB;
        this.userService = userService;
        this.docStorageLocation = Paths.get(documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        Files.createDirectories(this.docStorageLocation);
    }

    @Override
    public void deleteFile(Long id) {
        fileRepositoryDB.deleteById(id);
    }

    @Override
    public List<UploadedFile> listOfFiles(Long customUserId) {
        return fileRepositoryDB.findByCustomUserId(customUserId);
    }

    @Override
    public Path getDocStorageLocation() {
        return docStorageLocation;
    }

    @Override
    public UploadedFile uploadedFile(MultipartFile multipartFile) {
        try {
            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setFileName(multipartFile.getOriginalFilename());
            uploadedFile.setSizeFile(multipartFile.getSize());
            uploadedFile.setMimeType(multipartFile.getContentType());
            uploadedFile.setDate();
            uploadedFile.setOriginalFileName();
            uploadedFile.setCustomUserId(userService.getSpecifyUserId());
            return uploadedFile;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveUploadedFileToDB(UploadedFile uploadedFile){
        fileRepositoryDB.save(uploadedFile);
    }

    @Override
    public Optional<UploadedFile> findFileById(Long fileID) {
        return fileRepositoryDB.findById(fileID);
    }

    @Override
    public Path pathToSpecificFile(UploadedFile uploadedFile) {
        return getDocStorageLocation().resolve(uploadedFile.getOriginalFileName());
    }

    @Override
    public void savingFileToStorage(UploadedFile uploadedFile, MultipartFile multipartFile) throws Exception{
        File file = getDocStorageLocation().resolve(uploadedFile.getOriginalFileName()).toFile();
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        multipartFile.getInputStream().transferTo(outputStream);
        outputStream.close();
    }

    @Override
    public List<UploadedFile> findSearchingFiles(String keyword,Long customUserId) {
        return fileRepositoryDB.findByFileName(keyword,customUserId);
    }
}
