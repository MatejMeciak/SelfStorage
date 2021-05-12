package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Models.Folder;
import com.appslab.CloudService.Models.UploadedFile;
import com.appslab.CloudService.Repositories.FileRepositoryDB;
import com.appslab.CloudService.Services.FolderService;
import com.appslab.CloudService.Services.UploadFileService;
import com.appslab.CloudService.Services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FolderController {
    private FolderService folderService;
    private UploadFileService uploadFileService;
    private FileRepositoryDB fileRepositoryDB;
    private UserService userService;

    public FolderController(FolderService folderService, UploadFileService uploadFileService, FileRepositoryDB fileRepositoryDB) {
        this.folderService = folderService;
        this.uploadFileService = uploadFileService;
        this.fileRepositoryDB = fileRepositoryDB;
    }

    @PostMapping("/folder")
    public void createNewFolder(@RequestBody Folder folder){
        folderService.createFolder(folder);
    }

    @GetMapping("/folder/{folderName}")
    public List<UploadedFile> showContentInFolder(@PathVariable String folderName){
         Long folderId = folderService.findFolderByFolderName(folderName).get().getId();
         return fileRepositoryDB.findByFolderId(folderId);
    }

    @PutMapping("/folder/{folderName}")
    public void addFileToFolder( @PathVariable String folderName, @RequestBody UploadedFile uploadedFile){
        Folder folder = folderService.findFolderByFolderName(folderName).get();
        UploadedFile uploadedFile1 = uploadFileService.findFileById(uploadedFile.getId()).get();
        uploadedFile1.setFolderId(folder.getId());
        uploadFileService.saveEditFile(uploadedFile1);
    }

    @GetMapping("/search/folders")
    public List<Folder> folders(@Param("keyword") String keyword){
        Long specifiUserId = userService.getSpecifyUserId();
        return null;
    }
}
