package com.appslab.CloudService.Controllers;

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
import java.util.Optional;

@RequestMapping("/api/file")
@RestController
public class UploadFileController {
    UploadFileService uploadFileService;
    UserService userService;

    public UploadFileController(UploadFileService uploadFileService, UserService userService) {
        this.uploadFileService = uploadFileService;
        this.userService = userService;
    }

    @PostMapping
    public UploadedFile uploadFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        UploadedFile uploadedFile = uploadFileService.uploadedFile(multipartFile);
        uploadFileService.savingFileToStorage(uploadedFile,multipartFile);
        uploadFileService.saveUploadedFileToDB(uploadedFile);

        return uploadedFile;
    }

    @GetMapping
    public List<UploadedFile> listOfFiles(){
        Long specifyUserId = userService.getSpecifyUserId();
        return uploadFileService.listOfFiles(specifyUserId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        if(uploadedFile.getCustomUserId()==userService.getSpecifyUserId())
        {
            FileSystemResource file = new FileSystemResource(uploadFileService.pathToSpecificFile(uploadedFile));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadedFile.getMimeType())).body(new InputStreamResource(file.getInputStream()));
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        if (uploadedFile.getCustomUserId()== userService.getSpecifyUserId())
        {
            Files.delete(uploadFileService.pathToSpecificFile(uploadedFile));
            uploadFileService.deleteFile(id);
            return "File was succesfully deleted.";
        }
        return null;
    }

    @GetMapping("/search/")
    public List<UploadedFile> uploadedFile(@Param("keyword") String keyword){
        Long specifiUserId = userService.getSpecifyUserId();
        return uploadFileService.findSearchingFiles(keyword,specifiUserId);
    }

    @GetMapping("/edit")
    public String saveEditFile(@RequestBody UploadedFile uploadedFile) throws NoSuchAlgorithmException {
        Long idFile = uploadedFile.getId();
        UploadedFile uploadedFile1 = uploadFileService.findFileById(idFile).get();
        uploadedFile1.setFileName(uploadedFile.getFileName());
        uploadFileService.saveEditFile(uploadedFile1);
        return "File was succesfully rename.";
    }
}
