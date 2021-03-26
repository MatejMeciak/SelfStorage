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
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @GetMapping(value = "/{id}",produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<InputStreamResource> getFile(@PathVariable Long id) throws IOException{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        var file = new FileSystemResource("C:\\Users\\PC\\IdeaProjects\\cloud-service\\backend\\doc-uploads/"+uploadedFile.getHash());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).contentType(MediaType.IMAGE_GIF).contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(file.getInputStream()));
    }

    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable Long id)throws Exception{
        UploadedFile uploadedFile = uploadFileService.findFileById(id).get();
        Path deletingFile = Paths.get("C:\\Users\\PC\\IdeaProjects\\cloud-service\\backend\\doc-uploads/"+uploadedFile.getHash());
        Files.delete(deletingFile);
        uploadFileService.deleteFile(id);
        return "File is succesfully deleted";
    }

}
