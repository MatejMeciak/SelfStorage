package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Repositories.FileRepositoryDB;
import com.appslab.CloudService.Services.UploadFileService;
import com.appslab.CloudService.Models.UploadedFile;
import com.appslab.CloudService.Services.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequestMapping("/api/file")
@RestController
public class UploadFileController {
    private UploadFileService uploadFileService;
    private UserService userService;
    private FileRepositoryDB fileRepositoryDB;

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
    public ResponseEntity<InputStreamResource> getFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        if(uploadedFile.getCustomUserId().equals(userService.getSpecifyUserId()))
        {
            FileSystemResource file = new FileSystemResource(uploadFileService.pathToSpecificFile(uploadedFile));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadedFile.getMimeType())).body(new InputStreamResource(file.getInputStream()));
        }
        return null;
    }

    @GetMapping("/search/")
    public List<UploadedFile> uploadedFile(@Param("keyword") String keyword){
        Long specifiUserId = userService.getSpecifyUserId();
        return uploadFileService.findSearchingFiles(keyword,specifiUserId);
    }

    @GetMapping("/allFiles")
    public List<UploadedFile> publicFiles(){
        return fileRepositoryDB.findByAccess(true);
    }

    @GetMapping("/allFiles/search/")
    public List<UploadedFile> searchingInPublicFiles(@Param("keyword") String keyword){
        Boolean access = true;
        return uploadFileService.findSearchingFilesInPublicList(keyword,access);
    }

    @PostMapping
    public UploadedFile uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam(required = false) Boolean access) throws Exception {
        UploadedFile uploadedFile = uploadFileService.uploadedFile(multipartFile,access);
        uploadFileService.savingFileToStorage(uploadedFile,multipartFile);
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
    public UploadedFile saveEditFile(@RequestBody UploadedFile uploadedFile) throws NoSuchAlgorithmException {
        Long idFile = uploadedFile.getId();
        UploadedFile uploadedFile1 = uploadFileService.findFileById(idFile).get();
        uploadedFile1.setFileName(uploadedFile.getFileName());
        uploadFileService.saveEditFile(uploadedFile1);
        return uploadedFile1;
    }
}
