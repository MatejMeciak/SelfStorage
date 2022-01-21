package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.CategoryRepository;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.services.CategoryService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    UserService userservice;
    FileRepositoryDB fileRepositoryDB;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserService userService, FileRepositoryDB fileRepositoryDB) {
        this.categoryRepository = categoryRepository;
        this.userservice = userService;
        this.fileRepositoryDB = fileRepositoryDB;
    }

    @Override
    public List<Category> getListOfCategories() {
        return categoryRepository.findByCreatorId(userservice.getSpecifyUserId());
    }

    @Override
    public List<UploadedFile> getCategory(Long id) {
        return categoryRepository.findById(id).get().getFiles();
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
            UploadedFile uploadedFile1 = new UploadedFile();
            uploadedFile1.setCategoryId(categoryId);
            fileRepositoryDB.save(uploadedFile1);
            return uploadedFile1;
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
    }
}
