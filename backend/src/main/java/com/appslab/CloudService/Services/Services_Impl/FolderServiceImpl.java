package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.Models.Folder;
import com.appslab.CloudService.Repositories.FolderRepository;
import com.appslab.CloudService.Services.FolderService;
import com.appslab.CloudService.Services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FolderServiceImpl implements FolderService {

    private FolderRepository folderRepository;
    private UserService userService;

    public FolderServiceImpl(FolderRepository folderRepository, UserService userService) {
        this.folderRepository = folderRepository;
        this.userService = userService;
    }

    @Override
    public void createFolder(Folder folder) {
        folder.setDate();
        folder.setCustomUserId(userService.getSpecifyUserId());
        folderRepository.save(folder);
    }

    @Override
    public Optional<Folder> findFolderById(Long id) {
        return folderRepository.findById(id);
    }

    @Override
    public List<Folder> searchFoldersByFolderName(String keyword) {
        Long customUserId = userService.getSpecifyUserId();
        return folderRepository.findByFolderName(keyword,customUserId);
    }
}
