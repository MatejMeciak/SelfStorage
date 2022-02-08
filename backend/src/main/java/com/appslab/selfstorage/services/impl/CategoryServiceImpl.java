package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.CategoryRepository;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.FolderRepository;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements com.appslab.selfstorage.services.CategoryService {
    CategoryRepository categoryRepository;
    UserService userservice;
    FileRepositoryDB fileRepositoryDB;
    FolderRepository folderRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserService userService, FileRepositoryDB fileRepositoryDB, FolderRepository folderRepository) {
        this.categoryRepository = categoryRepository;
        this.userservice = userService;
        this.fileRepositoryDB = fileRepositoryDB;
        this.folderRepository = folderRepository;
    }

    @Override
    public List<Category> getListOfCategories() {
        return categoryRepository.findByCreatorId(userservice.getSpecifyUserId());
    }

    @Override
    public List<UploadedFile> getCategoryContent(String name) {
        return categoryRepository.findByName(name).getFiles();
    }

    @Override
    public Category createCategory(String categoryName){
        Category category = new Category();
        category.setCreatorId(userservice.getSpecifyUserId());
        category.setName(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public Object addContentToCategory(Long categoryId, Long requestId) {
        Category category = categoryRepository.findById(categoryId).get();
        if (category.getCreatorId().equals(userservice.getSpecifyUserId())){
            if(fileRepositoryDB.existsById(requestId)&&fileRepositoryDB.findById(requestId).equals(userservice.getSpecifyUserId())) {
                UploadedFile uploadedFile = fileRepositoryDB.findById(requestId).get();
                uploadedFile.setCategoryId(categoryId);
                fileRepositoryDB.save(uploadedFile);
                return uploadedFile;
            }
            else if(folderRepository.existsById(requestId)&&folderRepository.findById(requestId).equals(userservice.getSpecifyUserId())){
                Folder folder = folderRepository.findById(requestId).get();
                folder.setCategoryId(categoryId);
                folderRepository.save(folder);
                return folder;
            }
        }
        return null;
    }

    @Override
    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id).get();
        if (category.getCreatorId().equals(userservice.getSpecifyUserId())) {
            categoryRepository.delete(category);
        }
    }

    @Override
    public void deleteContentFromCategory(Long id) {
        if(fileRepositoryDB.findById(id).get().getOwnerId().equals(userservice.getSpecifyUserId())){
            UploadedFile uploadedFile = fileRepositoryDB.findById(id).get();
            uploadedFile.setCategoryId(null);
            fileRepositoryDB.save(uploadedFile);
        }
        else if(folderRepository.findById(id).get().getOwnerId().equals(userservice.getSpecifyUserId())){
            Folder folder = folderRepository.findById(id).get();
            folder.setCategoryId(null);
            folderRepository.save(folder);
        }
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }


}
