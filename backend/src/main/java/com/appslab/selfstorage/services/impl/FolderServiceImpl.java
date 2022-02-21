package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.CustomUser;
import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.FolderRepository;
import com.appslab.selfstorage.repositories.UserRepository;
import com.appslab.selfstorage.services.FolderService;
import com.appslab.selfstorage.services.UploadFileService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderServiceImpl implements FolderService {

    private FolderRepository folderRepository;
    private FileRepositoryDB fileRepositoryDB;
    private UploadFileService uploadFileService;
    private UserService userService;
    private UserRepository userRepository;

    public FolderServiceImpl(FolderRepository folderRepository,FileRepositoryDB fileRepositoryDB, UploadFileService uploadFileService, UserService userService, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.fileRepositoryDB = fileRepositoryDB;
        this.uploadFileService = uploadFileService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Override
    public List<UploadedFile> getFolderContent(Long id) {
        Folder folder = folderRepository.findById(id).get();
        if(folder.getOwnerId().equals(userService.getSpecifyUserId())){
            return fileRepositoryDB.findByFolderId(id);
        }
        return null;
    }

    @Override
    public Folder createFolder(Folder folder) {
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
            UploadedFile uploadedFile1 = fileRepositoryDB.findById(fileId).get();
            uploadedFile1.setFolderId(id);
            fileRepositoryDB.save(uploadedFile1);
            return folder;
        }
        return null;
    }

    @Override
    public Folder deleteFolder(Long id) {
        Folder folder = folderRepository.findById(id).get();
        if (folder.getOwnerId().equals(userService.getSpecifyUserId())) {
            List<UploadedFile> files = folder.getUploadedFileList();
            for(int i = 0;i<files.size();i++){
                UploadedFile file = fileRepositoryDB.findById(files.get(i).getId()).get();
                file.setFolderId(null);
                fileRepositoryDB.save(file);
            }
            folderRepository.deleteById(id);
            return folder;
        }
        return null;
    }

    @Override
    public UploadedFile deleteContent(Long folderId, Long id) {
        Folder folder = folderRepository.findById(folderId).get();
        if(fileRepositoryDB.existsById(id)==true&&fileRepositoryDB.findById(id).get().getOwnerId().equals(userService.getSpecifyUserId())){
            UploadedFile uploadedFile = fileRepositoryDB.findById(id).get();
            uploadedFile.setFolderId(null);
            return fileRepositoryDB.save(uploadedFile);
        }
        return null;
    }

    @Override
    public Folder shareFolderWithFriends(String email, Long id) {
        CustomUser user = userRepository.findByEmail(email);
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
        CustomUser user = userRepository.findById(userService.getSpecifyUserId()).get();
        List<Folder> getMySharedFolders = folderRepository.findByOwnerId(user.getId()).stream().filter(u -> u.getFriends() != null).collect(Collectors.toList());
        return getMySharedFolders;
    }

    @Override
    public List<Folder> getSharedFoldersFromOtherUsers() {
        CustomUser customUser = userRepository.findById(userService.getSpecifyUserId()).get();
        return customUser.getSharedFolder();
    }

    @Override
    public List<Folder> getPublicFolders() {
        return folderRepository.findByAccess(true);
    }

    @Override
    public Folder editFolder(Folder folder) {
        Folder folder1 = folderRepository.findById(folder.getId()).get();
        if(folder1.getOwnerId().equals(userService.getSpecifyUserId())){
            folder1.setName(folder.getName());
            folder1.setAccess(folder.getAccess());
            folderRepository.save(folder1);
        }
        return folder1;
    }
}