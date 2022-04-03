package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.config.DocumentStorageProperty;
import com.appslab.selfstorage.dto.FileBasicInfo;
import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.User;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.UserRepository;
import com.appslab.selfstorage.services.FileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    private FileRepositoryDB fileRepositoryDB;
    private Path docStorageLocation;
    private UserService userService;
    private UserRepository userRepository;


    public FileServiceImpl(FileRepositoryDB fileRepositoryDB, DocumentStorageProperty documentStorageProperty, UserService userService, UserRepository userRepository) throws Exception {
        this.fileRepositoryDB = fileRepositoryDB;
        this.userService = userService;
        this.docStorageLocation = Paths.get(documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        Files.createDirectories(this.docStorageLocation);
        this.userRepository = userRepository;
    }

    public Path pathToSpecificFile(File file) {
        return docStorageLocation.resolve(file.getUuid().toString());
    }

    @Override
    public File uploadedFile(MultipartFile multipartFile, Boolean access) {
        File file = new File();
        file.setName(multipartFile.getOriginalFilename());
        file.setFileSize(multipartFile.getSize());
        file.setMimeType(multipartFile.getContentType());
        file.setDate();
        file.setOwnerId(userService.getSpecifyUserId());
        file.setUuid();
        if (access!=null){
            file.setAccess(access);
        }
        return file;
    }


    @Override
    public FileBasicInfo deleteFile(Long id) throws Exception{
        File file = fileRepositoryDB.findById(id).get();
        if (file.getOwnerId().equals(userService.getSpecifyUserId()))
        {
            file.setFolderId(null);
            file.setCategories(null);
            file.setOwner(null);
            file.setFriends(null);
            file.setReports(null);
            fileRepositoryDB.save(file);
            fileRepositoryDB.deleteById(id);
            Files.delete(pathToSpecificFile(file));

            FileBasicInfo file1 = new FileBasicInfo();
            file1.setName(file1.getName());
            file1.setFileSize(file1.getFileSize());
            file1.setDate(file1.getDate());

            return file1;
        }
        return null;
    }

    @Override
    public List<File> getListOfMyFiles() {
        return fileRepositoryDB.findByOwnerId(userService.getSpecifyUserId()).stream().filter(y -> y.getOwnerProfilePicture()==null).collect(Collectors.toList());
    }

    @Override
    public void saveUploadedFileToDB(File file){
        fileRepositoryDB.save(file);
    }

    @Override
    public Optional<File> findFileById(Long fileID) {
        return fileRepositoryDB.findById(fileID);
    }

    @Override
    public void saveFileToStorage(File uploadedFile, MultipartFile multipartFile) throws Exception{
        java.io.File file = docStorageLocation.resolve(uploadedFile.getUuid().toString()).toFile();
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        multipartFile.getInputStream().transferTo(outputStream);
        outputStream.close();
    }

    @Override
    public List<File> findSearchFiles(String keyword) {
        return fileRepositoryDB.findByNameContainingAndOwnerId(keyword, userService.getSpecifyUserId());
    }

    @Override
    public File saveEditFile(FileBasicInfo fileBasicInfo) {
        File file1 = fileRepositoryDB.findById(fileBasicInfo.getId()).get();
        if(file1.getOwnerId().equals(userService.getSpecifyUserId()))
        {
            file1.setName(fileBasicInfo.getName());
            file1.setAccess(fileBasicInfo.isAccess());
            fileRepositoryDB.save(file1);
        }
        return file1;
    }

    @Override
    public List<File> findSearchFilesInPublicList(String keyword, Boolean access) {
        return fileRepositoryDB.findByNameContainingAndAccess(keyword,access);
    }

    @Override
    public ResponseEntity<InputStreamResource> getFile(File uploadedFile) throws Exception{
        if(uploadedFile.getOwnerId().equals(userService.getSpecifyUserId()))
        {
            FileSystemResource file = new FileSystemResource(pathToSpecificFile(uploadedFile));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadedFile.getMimeType())).body(new InputStreamResource(file.getInputStream()));
        }

        else if(uploadedFile.getFriends().contains(userService.getSpecifyUserId())){
            FileSystemResource file = new FileSystemResource(pathToSpecificFile(uploadedFile));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadedFile.getMimeType())).body(new InputStreamResource(file.getInputStream()));
        }
        return null;
    }

    @Override
    public void shareFileWithFriends(String email, Long id) {
        User user = userRepository.findByEmail(email);
        File file = fileRepositoryDB.findById(id).get();
        if(!file.getFriends().contains(user))
        {
            file.setFriends(user);
            fileRepositoryDB.save(file);
        }
    }

    @Override
    public List<File> getMySharedFiles() {
        User user = userRepository.findById(userService.getSpecifyUserId()).get();
        List<File> getMySharedFiles = fileRepositoryDB.findByOwnerId(user.getId()).stream().filter(u -> u.getFriends().size() != 0).collect(Collectors.toList());
        return getMySharedFiles;
    }

    @Override
    public List<File> getSharedFilesFromOtherUsers() {
        User user = userRepository.findById(userService.getSpecifyUserId()).get();
        return user.getSharedFiles();
    }

    @Override
    public List<File> getPublicFiles() {
        return fileRepositoryDB.findByAccess(true);
    }

    @Override
    public ResponseEntity<InputStreamResource> getPublicFile(Long id) throws Exception {
        File uploadedFile = fileRepositoryDB.findById(id).get();
        if (uploadedFile.getAccess().equals(true)) {
            FileSystemResource file = new FileSystemResource(pathToSpecificFile(uploadedFile));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadedFile.getMimeType())).body(new InputStreamResource(file.getInputStream()));
        }
        return null;
    }
    @Override
    public List<File> getFiles() {
        return fileRepositoryDB.findAllByFolderIdAndOwnerId(null, userService.getSpecifyUserId());
    }

    @Override
    public List<Category> categoriesFromFile(Long id) {
        return fileRepositoryDB.findById(id).get().getCategories();
    }

    @Override
    public List<File> getMySharedFilesWithCurrentUser(String email) {
        User friend = userRepository.findByEmail(email);
        User signInUser = userRepository.findById(userService.getSpecifyUserId()).get();

        return fileRepositoryDB.findByOwnerId(signInUser.getId())
                .stream().filter(u -> u.getFriends().contains(friend))
                .collect(Collectors.toList());
    }

    @Override
    public List<File> getSharedFilesFromSpecificFriend(String email) {
        User user = userRepository.findByEmail(email);
        Long currentUserId = userService.getUser().getId();

        return user.getSharedFiles().stream()
                .filter(y ->
                        y.getFriends().stream()
                                .map(User::getId)
                                .anyMatch(id -> Objects.equals(currentUserId, id))
                )
                .collect(Collectors.toList());
    }

    @Override
    public User updateProfilePicture(MultipartFile multipartFile) throws Exception {
        File file = uploadedFile(multipartFile,false);
        file.setOwnerId(null);
        User user = userService.getUser();
        fileRepositoryDB.deleteById(user.getProfilePicture().getId());

        Files.delete(docStorageLocation.resolve(userService.getUser().getProfilePicture().getUuid().toString()));

        user.setProfilePicture(file);
        file.setOwnerProfilePicture(user);
        saveFileToStorage(file,multipartFile);
        fileRepositoryDB.save(file);
        userRepository.save(user);
        return userService.getUser();
    }

    @Override
    public ResponseEntity<InputStreamResource> getProfilePicture() throws Exception {
        File file = userService.getUser().getProfilePicture();
        FileSystemResource view = new FileSystemResource(docStorageLocation.resolve(file.getUuid().toString()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getMimeType())).body(new InputStreamResource(view.getInputStream()));
    }

    @Override
    public User deleteProfilePicture() throws Exception{
        Files.delete(docStorageLocation.resolve(userService.getUser().getProfilePicture().getUuid().toString()));
        fileRepositoryDB.delete(userService.getUser().getProfilePicture());
        User currentUser = userService.getUser();
        userService.setDefaultProfilePicture(currentUser);
        return currentUser;
    }
}