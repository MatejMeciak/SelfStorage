package com.appslab.CloudService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class UploadFileController {
    UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService, FileRepository fileRepository) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile){
        UploadedFile file = new UploadedFile(multipartFile.getSize(), multipartFile.getOriginalFilename());
        uploadFileService.saveFile(file);
        return "File is success upload";
    }
    @GetMapping("/uploadedFiles")
    public List<UploadedFile> listOfFiles(){
        List<UploadedFile> files =uploadFileService.listOfFiles();
        return files;
    }

    @DeleteMapping("/deleteFile/{id}")
    public String deleteFile(@PathVariable Long id ){
        uploadFileService.deleteFile(id);
        return "File is success delete";
    }
}
