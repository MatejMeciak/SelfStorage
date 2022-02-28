package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.dto.FolderBasicInfo;
import com.appslab.selfstorage.models.User;
import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.FolderRepository;
import com.appslab.selfstorage.repositories.UserRepository;
import com.appslab.selfstorage.services.FolderService;
import com.appslab.selfstorage.services.FileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderServiceImpl implements FolderService {

    private FolderRepository folderRepository;
    private FileRepositoryDB fileRepositoryDB;
    private FileService fileService;
    private UserService userService;
    private UserRepository userRepository;

    public FolderServiceImpl(FolderRepository folderRepository, FileRepositoryDB fileRepositoryDB, FileService fileService, UserService userService, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.fileRepositoryDB = fileRepositoryDB;
        this.fileService = fileService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Override
    public List<File> getFolderContent(Long id) {
        Folder folder = folderRepository.findById(id).get();
        if(folder.getOwnerId().equals(userService.getSpecifyUserId())){
            return fileRepositoryDB.findByFolderId(id);
        }
        return null;
    }

    @Override
    public Folder createFolder(String name) {
        Folder folder = new Folder();
        folder.setName(name);
        folder.setDate();
        folder.setOwnerId(userService.getSpecifyUserId());
        return folderRepository.save(folder);
    }

    @Override
    public List<Folder> searchFoldersByFolderName(String keyword) {
        Long customUserId = userService.getSpecifyUserId();
        return folderRepository.findByNameContainingAndOwnerId(keyword, customUserId);
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

    @Override
    public Folder addContentToFolder(Long id, Long fileId ) {
        Folder folder = folderRepository.findById(id).get();
        if (folder.getOwnerId().equals(userService.getSpecifyUserId())) {
            File file1 = fileRepositoryDB.findById(fileId).get();
            file1.setFolderId(id);
            fileRepositoryDB.save(file1);
            return folder;
        }
        return null;
    }

    @Override
    public Folder deleteFolder(Long id) {
        Folder folder = folderRepository.findById(id).get();
        if (folder.getOwnerId().equals(userService.getSpecifyUserId())) {
            folder.setFileList(null);
            folder.setFriends(null);
            folder.setOwner(null);
            folderRepository.save(folder);
            folderRepository.deleteById(id);
            return folder;
        }
        return null;
    }

    @Override
    public File deleteContent(Long folderId, Long fileId) {
        Folder folder = folderRepository.findById(folderId).get();
        if(fileRepositoryDB.existsById(fileId)==true&&fileRepositoryDB.findById(fileId).get().getOwnerId().equals(userService.getSpecifyUserId())){
            File file = fileRepositoryDB.findById(fileId).get();
            file.setFolderId(null);
            return fileRepositoryDB.save(file);
        }
        return null;
    }

    @Override
    public Folder shareFolderWithFriends(String email, Long id) {
        User user = userRepository.findByEmail(email);
        Folder folder = folderRepository.findById(id).get();
        if(!folder.getFriends().contains(user))
        {
            folder.setFriends(user);
            return folderRepository.save(folder);
        }
        return null;
    }

    @Override
    public List<Folder> getMySharedFolders() {
        User user = userRepository.findById(userService.getSpecifyUserId()).get();
        List<Folder> getMySharedFolders = folderRepository.findByOwnerId(user.getId()).stream().filter(u -> u.getFriends().size() != 0).collect(Collectors.toList());
        return getMySharedFolders;
    }

    @Override
    public List<Folder> getSharedFoldersFromOtherUsers() {
        User user = userRepository.findById(userService.getSpecifyUserId()).get();
        return user.getSharedFolder();
    }

    @Override
    public List<Folder> getPublicFolders() {
        return folderRepository.findByAccess(true);
    }

    @Override
    public Folder editFolder(FolderBasicInfo folderBasicInfo) {
        Folder folder1 = folderRepository.findById(folderBasicInfo.getId()).get();
        if(folder1.getOwnerId().equals(userService.getSpecifyUserId())){
            folder1.setName(folderBasicInfo.getName());
            folder1.setAccess(folderBasicInfo.isAccess());
            folderRepository.save(folder1);
        }
        return folder1;
    }
}