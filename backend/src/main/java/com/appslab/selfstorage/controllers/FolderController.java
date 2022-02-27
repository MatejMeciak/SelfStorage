package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.services.FolderService;
import com.appslab.selfstorage.services.FileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService folderService;
    private final FileService fileService;
    private final FileRepositoryDB fileRepositoryDB;
    private final UserService userService;

    public FolderController(FolderService folderService, FileService fileService, FileRepositoryDB fileRepositoryDB, UserService userService) {
        this.folderService = folderService;
        this.fileService = fileService;
        this.fileRepositoryDB = fileRepositoryDB;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Folder getFolder(@PathVariable Long id){
        return folderService.getFolder(id);
    }

    @GetMapping("/{id}/content")
    public List<File> getContentInFolder(@PathVariable Long id){
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

    @GetMapping("/public")
    public List<Folder> publicFolders(){
        return folderService.getPublicFolders();
    }

    @PostMapping
    public Folder createNewFolder(@RequestParam String name){
        return folderService.createFolder(name);
    } // rename requestBody to requestParam(name)

    @PostMapping("/{id}/addFile")
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

    @DeleteMapping("/{id}/file")
    public File deleteFileFromFolder(@PathVariable Long id, @RequestParam Long fileId){
        return folderService.deleteContent(id,fileId);
    }

    @PutMapping("{id}/share")
    public Folder shareFolderWithFriends(@PathVariable Long id,@RequestParam String email){
        return folderService.shareFolderWithFriends(email, id);
    }

    @GetMapping("/shared/myFolders")
    public List<Folder> mySharedFolders(){
        return folderService.getMySharedFolders();
    }

    @GetMapping("/shared/fromFriends")
    public List<Folder> foldersFromFriends(){
        return folderService.getSharedFoldersFromOtherUsers();
    }

}