package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.services.FolderService;
import com.appslab.selfstorage.services.UploadFileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService folderService;
    private final UploadFileService uploadFileService;
    private final FileRepositoryDB fileRepositoryDB;
    private final UserService userService;

    public FolderController(FolderService folderService, UploadFileService uploadFileService, FileRepositoryDB fileRepositoryDB, UserService userService) {
        this.folderService = folderService;
        this.uploadFileService = uploadFileService;
        this.fileRepositoryDB = fileRepositoryDB;
        this.userService = userService;
    }

    @GetMapping("/{id}/content")
    public List<UploadedFile> getContentInFolder(@PathVariable Long id){
         return folderService.getFolderContent(id);
    }

    @GetMapping("/search")
    public List<Folder> searchFolders(@RequestParam String keyword){
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

    @GetMapping("/public")
    public List<Folder> pulicFolders(){
        return folderService.getPublicFolders();
    }

    @PostMapping
    public Folder createNewFolder(@RequestBody Folder folder){
        return folderService.createFolder(folder);
    }

    @PostMapping("/{id}/upload")
    public Folder addFileToFolder(@PathVariable Long id, @RequestParam Long fileId){
        return folderService.addContentToFolder(id, fileId);
    }

    @PutMapping("/{id}/edit")
    public Folder editFolder(@RequestBody Folder folder){
        return folderService.editFolder(folder);
    }

    @DeleteMapping("/{id}")
    public Folder deleteFolder(@PathVariable Long id){
        return folderService.deleteFolder(id);
    }

    @DeleteMapping("/file/{id}")
    public UploadedFile deleteFileFromFolder(@RequestParam Long folderId,@PathVariable Long id){
        return folderService.deleteContent(folderId,id);
    }

    @PutMapping("{id}/share")
    public Folder shareFolderWithFriends(@PathVariable Long id,@RequestParam String email){
        return folderService.shareFolderWithFriends(email, id);
    }

    @GetMapping("/share/myFolders")
    public List<Folder> mySharedFolders(){
        return folderService.getMySharedFolders();
    }

    @GetMapping("/share/fromFriends")
    public List<Folder> foldersFromFriends(){
        return folderService.getSharedFoldersFromOtherUsers();
    }

}