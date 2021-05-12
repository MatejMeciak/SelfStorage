package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.Models.Folder;
import com.appslab.CloudService.Repositories.FolderRepository;
import com.appslab.CloudService.Services.FolderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FolderServiceImpl implements FolderService {

    private FolderRepository folderRepository;

    public FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @Override
    public void createFolder(Folder folder) {
        folder.setDate();
        folderRepository.save(folder);
    }

    @Override
    public Optional<Folder> findFolderByFolderName(String folderName) {
        return folderRepository.findByFolderName(folderName);
    }
}
