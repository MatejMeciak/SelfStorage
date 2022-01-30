package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.services.UploadFileService;
import com.appslab.selfstorage.models.UploadedFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequestMapping("/api/file")
@RestController
public class UploadFileController {
    private final UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService, FileRepositoryDB fileRepositoryDB) {
        this.uploadFileService = uploadFileService;
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

    @GetMapping("/share/myFiles")
    public List<UploadedFile> getShareFiles(){
        return uploadFileService.returnShareFiles();
    }

    @GetMapping("/files")
    public List<UploadedFile> getFiles(){
        return uploadFileService.getFiles();
    }

    @PostMapping
    public UploadedFile uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam(required = false) Boolean access) throws Exception {
        UploadedFile uploadedFile = uploadFileService.uploadedFile(multipartFile,access);
        uploadFileService.saveFileToStorage(uploadedFile,multipartFile);
        uploadFileService.saveUploadedFileToDB(uploadedFile);
        return uploadedFile;
    }

    @DeleteMapping("/{id}")
    public UploadedFile deleteFile(@PathVariable Long id) throws Exception{
        return uploadFileService.deleteFile(id);
    }

    @PutMapping("/edit")
    public UploadedFile saveEditFile(@RequestBody UploadedFile uploadedFile){
        return uploadFileService.saveEditFile(uploadedFile);
    }

    @PutMapping("/share")
    public void saveEditFileWithUser(@RequestBody UploadedFile uploadedFile, @RequestParam String username){
        uploadFileService.saveEditFileWithUser(username, uploadedFile);
    }
}
