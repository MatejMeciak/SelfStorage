package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Services.UploadFileService;
import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;


@RestController
public class UploadFileController {
    UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        UploadedFile uploadedFile = uploadFileService.uploadedFile(multipartFile);
        File file = uploadFileService.getDocStorageLocation().resolve(uploadedFile.getHash()).toFile();
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        multipartFile.getInputStream().transferTo(outputStream);
        outputStream.close();

        uploadFileService.saveUploadedFileToDB(multipartFile);

        return "File is successfully uploaded";
    }

    @GetMapping("/uploadedFiles")
    public List<UploadedFile> listOfFiles(){
        List<UploadedFile> files = uploadFileService.listOfFiles();
        return files;
    }

    @GetMapping("/uploadedFiles/{id}")
    public UploadedFile downloadFile(@PathVariable Long id){
        UploadedFile findedFile = uploadFileService.findFileById(id).get();
        return findedFile;
    }

    @DeleteMapping("/deleteFile/{id}")
    public String deleteFile(@PathVariable Long id ){
        uploadFileService.deleteFile(id);
        return "File is successfully deleted";
    }

}
