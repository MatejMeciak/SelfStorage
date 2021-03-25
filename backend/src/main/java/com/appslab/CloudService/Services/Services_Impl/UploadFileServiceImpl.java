package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.FileProperty.DocumentStorageProperty;
import com.appslab.CloudService.Models.UploadedFile;
import com.appslab.CloudService.Repositories.FileRepositoryDB;
import com.appslab.CloudService.Services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    public UploadFileServiceImpl(FileRepositoryDB fileRepositoryDB, DocumentStorageProperty documentStorageProperty) throws Exception {
        this.fileRepositoryDB = fileRepositoryDB;
        this.docStorageLocation = Paths.get(documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        Files.createDirectories(this.docStorageLocation);
    }

    @Override
    public void saveFile(UploadedFile file) {
        fileRepositoryDB.save(file);
    }

    @Override
    public void deleteFile(Long id) {
        fileRepositoryDB.deleteById(id);
    }

    @Override
    public List<UploadedFile> listOfFiles() {
        return (List<UploadedFile>) fileRepositoryDB.findAll();
    }

    @Override
    public Path getDocStorageLocation() {
        return docStorageLocation;
    }

    @Override
    public UploadedFile uploadedFile(MultipartFile multipartFile) {
        try {
            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setNameFile(multipartFile.getOriginalFilename());
            uploadedFile.setSizeFile(multipartFile.getSize());
            uploadedFile.setMimeType(multipartFile.getContentType());
            uploadedFile.setHash();
            return uploadedFile;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveUploadedFileToDB(UploadedFile uploadedFile){
        saveFile(uploadedFile);
    }

    @Override
    public Optional<UploadedFile> findFileById(Long fileID) {
        return fileRepositoryDB.findById(fileID);
    }


}
