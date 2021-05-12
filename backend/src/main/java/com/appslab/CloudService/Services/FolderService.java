package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.Folder;

import java.util.Optional;

public interface FolderService {
    void createFolder(Folder folder);
    Optional<Folder> findFolderByFolderName(String folderName);
}
