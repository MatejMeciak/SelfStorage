package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.Link;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.FolderRepository;
import com.appslab.selfstorage.repositories.LinkRepository;
import com.appslab.selfstorage.services.FolderService;
import com.appslab.selfstorage.services.UploadFileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImpl implements FolderService {

    private FolderRepository folderRepository;
    private UserService userService;
    private FileRepositoryDB fileRepositoryDB;
    private UploadFileService uploadFileService;
    private LinkRepository linkRepository;

    public FolderServiceImpl(FolderRepository folderRepository, UserService userService, FileRepositoryDB fileRepositoryDB, UploadFileService uploadFileService, LinkRepository linkRepository) {
        this.folderRepository = folderRepository;
        this.userService = userService;
        this.fileRepositoryDB = fileRepositoryDB;
        this.uploadFileService = uploadFileService;
        this.linkRepository = linkRepository;
    }


    @Override
    public List<UploadedFile> getContentInFolder(Long id) {
        Folder folder = folderRepository.findById(id).get();
        if(folder.getOwnerId().equals(userService.getSpecifyUserId())){
            return folder.getUploadedFileList();
        }
        return null;
    }

    @Override
    public void createFolder(Folder folder) {
        folder.setDate();
        folder.setOwnerId(userService.getSpecifyUserId());
        folderRepository.save(folder);
    }

    @Override
    public List<Folder> searchFoldersByFolderName(String keyword) {
        Long customUserId = userService.getSpecifyUserId();
        return folderRepository.findByFolderName(keyword, customUserId);
    }

    @Override
    public List<Folder> getAllFolders() {
        return folderRepository.findByOwnerId(userService.getSpecifyUserId());
    }

    @Override
    public Folder getFolder(Long id) {
        Folder folder = folderRepository.findById(id).get();
        if (folder.getOwnerId().equals(userService.getSpecifyUserId())) {
            return folder;
        }
        return null;
    }

//    @Override
//    public UploadedFile addFileToFolder(Long id, UploadedFile uploadedFile) {
//        Folder folder = folderRepository.findById(id).get();
//        UploadedFile uploadedFile1 = fileRepositoryDB.findById(uploadedFile.getId()).get();
//        if (folder.getOwnerId().equals(userService.getSpecifyUserId()))
//        {
//            uploadedFile1.setFolderId(folder.getId());
//            uploadFileService.saveEditFile(uploadedFile1);
//            return uploadedFile1;
//        }
//        return null;
//    }
//
//    @Override
//    public Link addLinkToFolder(Long id, Link link) {
//        Folder folder = folderRepository.findById(id).get();
//        Link link1 =  linkRepository.findById(link.getId()).get();
//        if(folder.getOwnerId().equals(userService.getSpecifyUserId())){
//            link1.setFolderId(folder.getId());
//            linkRepository.save(link1);
//            return link1;
//        }
//        return null;
//    }

    @Override
    public void addContentToFolder(Long id, Long requestId ) {
        Folder folder = folderRepository.findById(id).get();
        if (folder.getOwnerId().equals(userService.getSpecifyUserId())){
            if (fileRepositoryDB.existsById(requestId)==true) {
                UploadedFile uploadedFile1 = fileRepositoryDB.findById(requestId).get();
                uploadedFile1.setFolderId(id);
                fileRepositoryDB.save(uploadedFile1);
            } else if (linkRepository.existsById(requestId)==true) {
                Link link1 = linkRepository.findById(requestId).get();
                link1.setFolderId(id);
                linkRepository.save(link1);
            }
        }
    }

    @Override
    public Folder deleteFolder(Long id) {
        Folder folder = folderRepository.findById(id).get();
        if (folder.getOwnerId().equals(userService.getSpecifyUserId())) {
            folderRepository.deleteById(id);
            return folder;
        }
        return null;
    }

    @Override
    public void deleteContent(Long folderId, Long id) {
        Folder folder = folderRepository.findById(folderId).get();
        if(fileRepositoryDB.existsById(id)==true&linkRepository.findById(id).get().getOwnerId().equals(userService.getSpecifyUserId())){
            fileRepositoryDB.findById(id).get().setFolderId(null);
        }
        else if(linkRepository.existsById(id)==true&linkRepository.findById(id).get().getOwnerId().equals(userService.getSpecifyUserId())){
            linkRepository.findById(id).get().setFolderId(null);
        }
    }
}