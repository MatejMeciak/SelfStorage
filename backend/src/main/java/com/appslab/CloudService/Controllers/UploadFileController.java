package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Services.UploadFileService;
import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

@RequestMapping("/api/file")
@RestController
public class UploadFileController {
    UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        UploadedFile uploadedFile = uploadFileService.uploadedFile(multipartFile);
        File file = uploadFileService.getDocStorageLocation().resolve(uploadedFile.getHash()).toFile();
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        multipartFile.getInputStream().transferTo(outputStream);
        outputStream.close();

        uploadFileService.saveUploadedFileToDB(uploadedFile);

        return "File is successfully uploaded";
    }

    @GetMapping
    public List<UploadedFile> listOfFiles(){
        return uploadFileService.listOfFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        FileSystemResource file = new FileSystemResource(uploadFileService.pathToSpecificFile(uploadedFile));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadedFile.getMimeType())).body(new InputStreamResource(file.getInputStream()));
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        Files.delete(uploadFileService.pathToSpecificFile(uploadedFile));
        uploadFileService.deleteFile(id);
    }

}
