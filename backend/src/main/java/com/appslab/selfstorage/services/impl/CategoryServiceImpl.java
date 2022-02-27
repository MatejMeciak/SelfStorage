package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.File;
import com.appslab.selfstorage.repositories.CategoryRepository;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.FolderRepository;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<File> getFilesInCategory(Long id) {
        return categoryRepository.findById(id).get().getFiles();
    }

    @Override
    public List<Folder> getFoldersInCategory(Long id){
        return categoryRepository.findById(id).get().getFolders();
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
            if(fileRepositoryDB.existsById(requestId)&&fileRepositoryDB.findById(requestId).get().getOwnerId().equals(userservice.getSpecifyUserId())) {
                File file = fileRepositoryDB.findById(requestId).get();

                List<Category> categoryList = fileRepositoryDB.findById(requestId).get().getCategories();
                categoryList.add(category);

                file.setCategories(categoryList.stream().distinct().collect(Collectors.toList()));
                fileRepositoryDB.save(file);

                return file;
            }
            else if(folderRepository.existsById(requestId)&&folderRepository.findById(requestId).get().getOwnerId().equals(userservice.getSpecifyUserId())){
                Folder folder = folderRepository.findById(requestId).get();

                List<Category> categoryList = folderRepository.findById(requestId).get().getCategories();
                categoryList.add(category);

                folder.setCategories(categoryList.stream().distinct().collect(Collectors.toList()));
                folderRepository.save(folder);

                return folder;
            }
        }
        return null;
    }

    @Override
    public Category deleteCategory(Long id){
        Category category = categoryRepository.findById(id).get();
        if (category.getCreatorId().equals(userservice.getSpecifyUserId())) {
            categoryRepository.delete(category);
            return category;
        }
        return null;
    }

    @Override
    public Object deleteContentFromCategory(Long categoryId, Long id) {
        if(fileRepositoryDB.existsById(id)&&fileRepositoryDB.findById(id).get().getOwnerId().equals(userservice.getSpecifyUserId())){
            File file = fileRepositoryDB.findById(id).get();
            List<Category> categoryList = file.getCategories();
            categoryList.remove(categoryId.compareTo(categoryId));
            file.setCategories(categoryList);
            return fileRepositoryDB.save(file);
        }
        else if(folderRepository.existsById(id)&&folderRepository.findById(id).get().getOwnerId().equals(userservice.getSpecifyUserId())){
            Folder folder = folderRepository.findById(id).get();
            List<Category> categoryList = folder.getCategories();
            categoryList.remove(categoryId.compareTo(categoryId));
            folder.setCategories(categoryList);
            return folderRepository.save(folder);
        }
        return null;
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }


}
