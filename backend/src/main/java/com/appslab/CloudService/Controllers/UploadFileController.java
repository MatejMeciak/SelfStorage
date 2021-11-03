package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Repositories.FileRepositoryDB;
import com.appslab.CloudService.Services.UploadFileService;
import com.appslab.CloudService.Models.UploadedFile;
import com.appslab.CloudService.Services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/file")
@RestController
public class UploadFileController {
    private final UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService, FileRepositoryDB fileRepositoryDB) {
        this.uploadFileService = uploadFileService;
    }

    @GetMapping
    public List<UploadedFile> getListOfFiles(){
        return uploadFileService.getListOfMyFiles();
    }

    @GetMapping("/{id}")
    public Object getFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();

        return uploadFileService.getFile(uploadedFile);
    }

    @GetMapping("/search")
    public List<UploadedFile> getSearchFiles(@Param("keyword") String keyword){
        return uploadFileService.findSearchFiles(keyword);
    }

    @GetMapping("/allFiles")
    public List<UploadedFile> getPublicFiles(){
        return uploadFileService.getPublicFiles();
    }

    @GetMapping("/allFiles/search")
    public List<UploadedFile> searchInPublicFiles(@Param("keyword") String keyword){
        return uploadFileService.findSearchFilesInPublicList(keyword,true);
    }

    @GetMapping("/share/myFiles")
    public List<UploadedFile> getShareFiles(){
        return uploadFileService.returnShareFiles();
    }

//    @GetMapping("/getAllLinks")
//    public List<UploadedFile> getAllLinks(){
//        List<UploadedFile> uploadedFiles = uploadFileService.listOfFiles(userService.getSpecifyUserId());
//        //List<UploadedFile> uploadedFileWithLink = uploadedFiles.stream().filter(y->y.getLink()!=null).collect(Collectors.toList());
//        return uploadedFileWithLink;
//    }

    @PostMapping
    public UploadedFile uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam(required = false) Boolean access) throws Exception {
        UploadedFile uploadedFile = uploadFileService.uploadedFile(multipartFile,access);
        uploadFileService.saveFileToStorage(uploadedFile,multipartFile);
        uploadFileService.saveUploadedFileToDB(uploadedFile);

        return uploadedFile;
    }

//    @PostMapping("/uploadLink")
//    public UploadedFile uploadLinkToFile(@RequestBody UploadedFile uploadedFile){
//        uploadedFile.setCustomUserId(userService.getSpecifyUserId());
//        uploadedFile.setDate();
//        fileRepositoryDB.save(uploadedFile);
//        return uploadedFile;
//    }

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
