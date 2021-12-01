package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Models.Folder;
import com.appslab.CloudService.Models.UploadedFile;
import com.appslab.CloudService.Repositories.FileRepositoryDB;
import com.appslab.CloudService.Services.FolderService;
import com.appslab.CloudService.Services.UploadFileService;
import com.appslab.CloudService.Services.UserService;
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

    @GetMapping("/{id}/content")
    public List<UploadedFile> getContentInFolder(@PathVariable Long id){
         return folderService.getContentInFolder(id);
    }

    @GetMapping("/search")
    public List<Folder> searchFolders(@RequestParam("keyword") String keyword){
        return folderService.searchFoldersByFolderName(keyword);
    }

    @GetMapping("/allFolder")
    public List<Folder> getAllFolders(){
        return folderService.getAllFolders();
    }

    @GetMapping("/getFolder/{id}")
    public Folder getFolder(@PathVariable Long id){
        return folderService.getFolder(id);
    }

    @PostMapping
    public void createNewFolder(@RequestBody Folder folder){
        folderService.createFolder(folder);
    }

    @PutMapping("/{id}/upload")
    public void addFileOrLinkToFolder(@PathVariable Long id, @RequestParam Long requestId){
        folderService.addContentToFolder(id, requestId);
    }

    @DeleteMapping("/{id}")
    public Folder deleteFolder(@PathVariable Long id){
        return folderService.deleteFolder(id);
    }

    @DeleteMapping("/folder/{id}")
    public void deleteLinkOrFileFromFolder(@RequestParam Long folderId,@PathVariable Long id){
        folderService.deleteContent(folderId,id);
    }
}