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
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService folderService;
    private final UploadFileService uploadFileService;
    private final FileRepositoryDB fileRepositoryDB;
    private final UserService userService;

    public FolderController(FolderService folderService, UploadFileService uploadFileService, FileRepositoryDB fileRepositoryDB,UserService userService) {
        this.folderService = folderService;
        this.uploadFileService = uploadFileService;
        this.fileRepositoryDB = fileRepositoryDB;
        this.userService = userService;
    }

    @PostMapping
    public void createNewFolder(@RequestBody Folder folder){
        folderService.createFolder(folder);
    }

    @GetMapping("/{id}")
    public List<UploadedFile> showContentInFolder(@PathVariable Long id){
         Long folderId = folderService.findFolderById(id).get().getId();
         if(folderService.findFolderById(id).get().getCustomUserId().equals(userService.getSpecifyUserId()))
         {
             return fileRepositoryDB.findByFolderId(folderId);
         }
         return null;
    }

    @PutMapping("/{id}")
    public UploadedFile addFileToFolder(@PathVariable Long id, @RequestBody UploadedFile uploadedFile){
        Folder folder = folderService.findFolderById(id).get();
        UploadedFile uploadedFile1 = uploadFileService.findFileById(uploadedFile.getId()).get();
        if (folder.getCustomUserId().equals(userService.getSpecifyUserId()))
        {
            uploadedFile1.setFolderId(folder.getId());
            uploadFileService.saveEditFile(uploadedFile1);
            return uploadedFile1;
        }
        return null;
    }

    @GetMapping("/search")
    public List<Folder> searchingfolders(@Param("keyword") String keyword){
        return folderService.searchingFoldersByFolderName(keyword);
    }
}
