package com.appslab.CloudService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UploadUploadFileServiceImpl implements UploadFileService {
    FileRepository fileRepository;

    public UploadUploadFileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void saveFile(UploadedFile file) {
        fileRepository.save(file);
    }

    @Override
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    @Override
    public List<UploadedFile> listOfFiles() {
        return (List<UploadedFile>) fileRepository.findAll();
    }
}
