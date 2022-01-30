package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.config.DocumentStorageProperty;
import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.UserRepository;
import com.appslab.selfstorage.services.UploadFileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    private FileRepositoryDB fileRepositoryDB;
    private Path docStorageLocation;
    private UserService userService;
    private UserRepository userRepository;


    public UploadFileServiceImpl(FileRepositoryDB fileRepositoryDB, DocumentStorageProperty documentStorageProperty, UserService userService, UserRepository userRepository) throws Exception {
        this.fileRepositoryDB = fileRepositoryDB;
        this.userService = userService;
        this.docStorageLocation = Paths.get(documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        Files.createDirectories(this.docStorageLocation);
        this.userRepository = userRepository;
    }

    @Override
    public UploadedFile deleteFile(Long id) throws Exception{
        UploadedFile uploadedFile = fileRepositoryDB.findById(id).get();
        if (uploadedFile.getOwnerId().equals(userService.getSpecifyUserId()))
        {
            if (uploadedFile.getUuid()!= null){
                Files.delete(pathToSpecificFile(uploadedFile));
            }
            fileRepositoryDB.deleteById(id);
            return uploadedFile;
        }
        return null;
    }

    @Override
    public List<UploadedFile> getListOfMyFiles() {
        return fileRepositoryDB.findByOwnerId(userService.getSpecifyUserId());
    }

    @Override
    public UploadedFile uploadedFile(MultipartFile multipartFile,Boolean access) {
            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setName(multipartFile.getOriginalFilename());
            uploadedFile.setFileSize(multipartFile.getSize());
            uploadedFile.setMimeType(multipartFile.getContentType());
            uploadedFile.setDate();
            uploadedFile.setOwnerId(userService.getSpecifyUserId());
            uploadedFile.setUuid();
            if (access!=null){
                uploadedFile.setAccess(access);
            }
            return uploadedFile;
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
        return docStorageLocation.resolve(uploadedFile.getUuid().toString());
    }

    @Override
    public void saveFileToStorage(UploadedFile uploadedFile, MultipartFile multipartFile) throws Exception{
        File file = docStorageLocation.resolve(uploadedFile.getUuid().toString()).toFile();
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        multipartFile.getInputStream().transferTo(outputStream);
        outputStream.close();
    }

    @Override
    public List<UploadedFile> findSearchFiles(String keyword) {
        return fileRepositoryDB.findByNameContainingAndOwnerId(keyword, userService.getSpecifyUserId());
    }

    @Override
    public UploadedFile saveEditFile(UploadedFile uploadedFile) {
        UploadedFile uploadedFile1 = fileRepositoryDB.findById(uploadedFile.getId()).get();
        if(uploadedFile1.getOwnerId().equals(userService.getSpecifyUserId()))
        {
            uploadedFile1.setName(uploadedFile.getName());
            uploadedFile1.setAccess(uploadedFile.getAccess());
            fileRepositoryDB.save(uploadedFile1);
        }
        return uploadedFile1;
    }

    @Override
    public List<UploadedFile> findSearchFilesInPublicList(String keyword,Boolean access) {
        return fileRepositoryDB.findByNameContainingAndAccess(keyword,access);
    }

    @Override
    public ResponseEntity<InputStreamResource> getFile(UploadedFile uploadedFile) throws Exception{
        if(uploadedFile.getOwnerId().equals(userService.getSpecifyUserId()))
        {
            FileSystemResource file = new FileSystemResource(pathToSpecificFile(uploadedFile));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadedFile.getMimeType())).body(new InputStreamResource(file.getInputStream()));
        }
        else if(uploadedFile.getAccess().equals(true))
        {
            FileSystemResource file = new FileSystemResource(pathToSpecificFile(uploadedFile));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadedFile.getMimeType())).body(new InputStreamResource(file.getInputStream()));
        }
        return null;
    }

    @Override
    public void saveEditFileWithUser(String username, UploadedFile uploadedFile) {
        CustomUser user = userRepository.findByUsername(username).get();
        UploadedFile uploadedFile1 = fileRepositoryDB.findById(uploadedFile.getId()).get();
        if(!uploadedFile1.getFriends().contains(user))
        {
            uploadedFile1.setFriends(user);
            fileRepositoryDB.save(uploadedFile1);
        }
    }

    @Override
    public List<UploadedFile> returnShareFiles() {
        CustomUser customUser = userRepository.findById(userService.getSpecifyUserId()).get();
        return fileRepositoryDB.findByOwner(customUser);
    }

    @Override
    public List<UploadedFile> getPublicFiles() {
        return fileRepositoryDB.findByAccess(true);
    }

    @Override
    public List<UploadedFile> getFiles() {
        return fileRepositoryDB.findAllByFolderId(null);
    }
}