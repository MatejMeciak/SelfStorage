package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Services.UploadFileService;
import com.appslab.CloudService.Models.UploadedFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RequestMapping("/file")
@RestController
public class UploadFileController {
    UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/")
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

    @GetMapping("/")
    public List<UploadedFile> listOfFiles(){
        return uploadFileService.listOfFiles();
    }

    @GetMapping("/uploadedFile/{id}")
    public String downloadFile(@PathVariable Long id) throws IOException{
        try {
            byte[] array = new byte[100000];
            UploadedFile findedFile = uploadFileService.findFileById(id).get();
            InputStream fileInputStream = new FileInputStream(findedFile.getNameFile());
            fileInputStream.read(array);
            String content = new String(array);
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/deleteFile/{id}")
    public String deleteFile(@PathVariable Long id ){
        uploadFileService.deleteFile(id);
        return "File is successfully deleted";
    }

}
