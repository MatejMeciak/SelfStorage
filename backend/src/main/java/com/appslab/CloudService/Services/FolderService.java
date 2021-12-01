package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.Folder;
import com.appslab.CloudService.Models.Link;
import com.appslab.CloudService.Models.UploadedFile;

import java.util.List;
import java.util.Optional;

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
