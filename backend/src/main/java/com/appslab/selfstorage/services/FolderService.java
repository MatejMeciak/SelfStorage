package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.UploadedFile;

import java.util.List;

public interface FolderService {

    List<UploadedFile> getFolderContent(Long id);

    void createFolder(Folder folder);

    List<Folder> searchFoldersByFolderName(String keyword);

    List<Folder> getAllFolders();

    Folder getFolder(Long id);

    void addContentToFolder(Long id, Long fileId);

    Folder deleteFolder(Long id);

    void deleteContent(Long folderId,Long id);

    void shareFolderWithFriends(String email, Long id);

    List<Folder> getMySharedFolders();

    List<Folder> getSharedFoldersFromOtherUsers();

    List<Folder> getPublicFolders();

    Folder editFolder(Folder folder);
}
