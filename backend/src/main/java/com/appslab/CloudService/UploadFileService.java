package com.appslab.CloudService;

import java.util.List;
import java.util.Optional;

public interface UploadFileService {
    void saveFile(UploadedFile file);
    void deleteFile(Long id);
    List<UploadedFile> listOfFiles();
}
