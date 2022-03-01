package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.dto.FileBasicInfo;
import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.User;
import com.appslab.selfstorage.services.FileService;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequestMapping("/api/file")
@RestController
public class FileController {
    private final FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public List<File> getListOfAllFiles(){
        return fileService.getListOfMyFiles();
    }

    @GetMapping("/files")
    public List<File> getFiles(){
        return fileService.getFiles();
    }

    @GetMapping("/{id}")
    public Object getFile(@PathVariable Long id) throws Exception{
        File file = fileService.findFileById(id).get();
        return fileService.getFile(file);
    }

    @GetMapping("/search")
    public List<File> getSearchedFiles(@RequestParam String keyword){
        return fileService.findSearchFiles(keyword);
    }

    @GetMapping("/public")
    public List<File> getPublicFiles(){
        return fileService.getPublicFiles();
    }

    @GetMapping("/public/search")
    public List<File> searchInPublicFiles(@RequestParam String keyword){
        return fileService.findSearchFilesInPublicList(keyword,true);
    }

    @GetMapping("/public/{id}")
    public Object getPublicFile(@PathVariable Long id) throws Exception{
        return fileService.getPublicFile(id);
    }

    @GetMapping("/shared/myFiles")
    public List<File> getSharedFiles(){
        return fileService.getMySharedFiles();
    }

    @GetMapping("/shared/fromFriends")
    public List<File> getFilesFromFriends(){
        return fileService.getSharedFilesFromOtherUsers();
    }

    @GetMapping("/shared/myFiles/friend")
    public List<File> getMySharedFilesWithCurrentUser(@RequestParam String email){
        return fileService.getMySharedFilesWithCurrentUser(email);
    }

    @GetMapping("/categories")
    public List<Category> fileCategories(@RequestParam Long id){
        return fileService.categoriesFromFile(id);
    }

    @PutMapping("/edit")
    public File saveEditFile(@RequestBody FileBasicInfo fileBasicInfo){ // in requestBody file or dto
        return fileService.saveEditFile(fileBasicInfo);
    }

    @PutMapping("/{id}/share")
    public void shareFileWithUser(@PathVariable Long id, @RequestParam String email){
        fileService.shareFileWithFriends(email, id);
    }

    @PostMapping
    public File uploadFile(@RequestBody MultipartFile multipartFile, @RequestParam(required = false) Boolean access) throws Exception {
        File file = fileService.uploadedFile(multipartFile,access);
        User signInUser = userService.getUser();
        if (file.getFileSize()+userService.usedSpaceOfStorage()<= signInUser.getSpaceSize()) {
            fileService.saveFileToStorage(file, multipartFile);
            fileService.saveUploadedFileToDB(file);
            return file;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public FileBasicInfo deleteFile(@PathVariable Long id) throws Exception{
        return fileService.deleteFile(id);
    }
}
