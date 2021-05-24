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

@RequestMapping("/api/file")
@RestController
public class UploadFileController {
    private final UploadFileService uploadFileService;
    private final UserService userService;
    private final FileRepositoryDB fileRepositoryDB;

    public UploadFileController(UploadFileService uploadFileService, UserService userService, FileRepositoryDB fileRepositoryDB) {
        this.uploadFileService = uploadFileService;
        this.userService = userService;
        this.fileRepositoryDB = fileRepositoryDB;
    }

    @GetMapping
    public List<UploadedFile> listOfFiles(){
        Long specifyUserId = userService.getSpecifyUserId();
        return uploadFileService.listOfFiles(specifyUserId);
    }

    @GetMapping("/{id}")
    public Object getFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();

        return uploadFileService.returnUploadedFileOrLink(uploadedFile);
    }

    @GetMapping("/search")
    public List<UploadedFile> uploadedFile(@Param("keyword") String keyword){
        Long specifiUserId = userService.getSpecifyUserId();
        return uploadFileService.findSearchFiles(keyword,specifiUserId);
    }

    @GetMapping("/allFiles")
    public List<UploadedFile> publicFiles(){
        return fileRepositoryDB.findByAccess(true);
    }

    @GetMapping("/allFiles/search")
    public List<UploadedFile> searchInPublicFiles(@Param("keyword") String keyword){
        Boolean access = true;
        return uploadFileService.findSearchFilesInPublicList(keyword,access);
    }

    @GetMapping("/share/myFiles")
    public List<UploadedFile> getShareFiles(){
        return uploadFileService.returnShareFiles();
    }

    @PostMapping
    public UploadedFile uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam(required = false) Boolean access) throws Exception {
        UploadedFile uploadedFile = uploadFileService.uploadedFile(multipartFile,access);
        uploadFileService.saveFileToStorage(uploadedFile,multipartFile);
        uploadFileService.saveUploadedFileToDB(uploadedFile);

        return uploadedFile;
    }

    @PostMapping("/uploadLink")
    public UploadedFile uploadLinkToFile(@RequestBody UploadedFile uploadedFile){
        uploadedFile.setCustomUserId(userService.getSpecifyUserId());
        uploadedFile.setDate();
        fileRepositoryDB.save(uploadedFile);
        return uploadedFile;
    }

    @DeleteMapping("/{id}")
    public UploadedFile deleteFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        if (uploadedFile.getCustomUserId().equals(userService.getSpecifyUserId()))
        {
            Files.delete(uploadFileService.pathToSpecificFile(uploadedFile));
            uploadFileService.deleteFile(id);
            return uploadedFile;
        }
        return null;
    }

    @PutMapping("/edit")
    public UploadedFile saveEditFile(@RequestBody UploadedFile uploadedFile){
        UploadedFile uploadedFile1 = uploadFileService.findFileById(uploadedFile.getId()).get();
        if(uploadedFile1.getCustomUserId().equals(userService.getSpecifyUserId()))
        {
            uploadedFile1.setFileName(uploadedFile.getFileName());
            uploadedFile1.setAccess(uploadedFile.getAccess());
            uploadFileService.saveEditFile(uploadedFile1);
       }
        return uploadedFile1;
    }

    @PutMapping("/share")
    public void saveEditFileWithUser(@RequestBody UploadedFile uploadedFile, @RequestParam String username){
        uploadFileService.saveEditFileWithUser(username, uploadedFile);
    }
}
