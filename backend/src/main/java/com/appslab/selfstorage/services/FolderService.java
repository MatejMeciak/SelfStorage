package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.UploadedFile;

import java.util.List;

public interface FolderService {

    List<UploadedFile> getContentInFolder(Long id);

    void createFolder(Folder folder);

    List<Folder> searchFoldersByFolderName(String keyword);

    List<Folder> getAllFolders();

    Folder getFolder(Long id);

    void addContentToFolder(Long id, Long requestId);

    Folder deleteFolder(Long id);

    void deleteContent(Long folderId,Long id);
}
