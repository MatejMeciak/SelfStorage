package com.appslab.selfstorage.services;

import com.appslab.selfstorage.dto.FolderBasicInfo;
import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.File;

import java.util.List;

public interface FolderService {

    List<File> getFolderContent(Long id);

    Folder createFolder(String name);

    List<Folder> searchFoldersByFolderName(String keyword);

    List<Folder> getAllFolders();

    Folder getFolder(Long id);

    Folder addContentToFolder(Long id, Long fileId);

    Folder deleteFolder(Long id);

    File deleteContent(Long folderId, Long fileId);

    Folder shareFolderWithFriends(String email, Long id);

    List<Folder> getMySharedFolders();

    List<Folder> getSharedFoldersFromOtherUsers();

    List<Folder> getPublicFolders();

    Folder editFolder(FolderBasicInfo folderBasicInfo);
}
