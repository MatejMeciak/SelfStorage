package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.dto.FileBasicInfo;
import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.services.UploadFileService;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequestMapping("/api/file")
@RestController
public class UploadFileController {
    private final UploadFileService uploadFileService;
    private UserService userService;

    public UploadFileController(UploadFileService uploadFileService, UserService userService) {
        this.uploadFileService = uploadFileService;
        this.userService = userService;
    }

    @GetMapping
    public List<UploadedFile> getListOfAllFiles(){
        return uploadFileService.getListOfMyFiles();
    }

    @GetMapping("/{id}")
    public Object getFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        return uploadFileService.getFile(uploadedFile);
    }

    @GetMapping("/search")
    public List<UploadedFile> getSearchedFiles(@RequestParam String keyword){
        return uploadFileService.findSearchFiles(keyword);
    }

    @GetMapping("/public")
    public List<UploadedFile> getPublicFiles(){
        return uploadFileService.getPublicFiles();
    }

    @GetMapping("/public/search")
    public List<UploadedFile> searchInPublicFiles(@RequestParam String keyword){
        return uploadFileService.findSearchFilesInPublicList(keyword,true);
    }

    @GetMapping("/public/{id}")
    public Object getPublicFile(@PathVariable Long id) throws Exception{
        return uploadFileService.getPublicFile(id);
    }

    @GetMapping("/share/myFiles")
    public List<UploadedFile> getSharedFiles(){
        return uploadFileService.getMySharedFiles();
    }

    @GetMapping("/files")
    public List<UploadedFile> getFiles(){
        return uploadFileService.getFiles();
    }

    @GetMapping("/share/fromFriends")
    public List<UploadedFile> getfilesFromFriends(){
        return uploadFileService.getSharedFilesFromOtherUsers();
    }

    @GetMapping("/categories")
    public List<Category> categoriesFromFile(@RequestParam Long id){
        return uploadFileService.categoriesFromFile(id);
    }

    @PutMapping("/edit")
    public UploadedFile saveEditFile(@RequestBody UploadedFile uploadedFile){
        return uploadFileService.saveEditFile(uploadedFile);
    }

    @PutMapping("/{id}/share")
    public void shareFileWithUser(@PathVariable Long id, @RequestParam String email){
        uploadFileService.shareFileWithFriends(email, id);
    }

    @PostMapping
    public UploadedFile uploadFile(@RequestBody MultipartFile multipartFile,@RequestParam(required = false) Boolean access) throws Exception {
        UploadedFile uploadedFile = uploadFileService.uploadedFile(multipartFile,access);
        CustomUser signInUser = userService.getUser();
        if (uploadedFile.getFileSize()+userService.usedSpaceOfStorage()<= signInUser.getSpaceSize()) {
            uploadFileService.saveFileToStorage(uploadedFile, multipartFile);
            uploadFileService.saveUploadedFileToDB(uploadedFile);
            return uploadedFile;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public FileBasicInfo deleteFile(@PathVariable Long id) throws Exception{
        return uploadFileService.deleteFile(id);
    }
}
