package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.Link;
import com.appslab.selfstorage.models.UploadedFile;
import com.appslab.selfstorage.repositories.CategoryRepository;
import com.appslab.selfstorage.repositories.FileRepositoryDB;
import com.appslab.selfstorage.repositories.LinkRepository;
import com.appslab.selfstorage.services.CategoryService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    UserService userservice;
    FileRepositoryDB fileRepositoryDB;
    LinkRepository linkRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserService localUserDetailService, FileRepositoryDB fileRepositoryDB, LinkRepository linkRepository) {
        this.categoryRepository = categoryRepository;
        this.userservice = localUserDetailService;
        this.fileRepositoryDB = fileRepositoryDB;
        this.linkRepository = linkRepository;
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
            if(fileRepositoryDB.existsById(requestId)==true) {
                UploadedFile uploadedFile1 = new UploadedFile();
                uploadedFile1.setCategoryId(categoryId);
                fileRepositoryDB.save(uploadedFile1);
                return uploadedFile1;
            }
            else if (linkRepository.existsById(requestId)==true){
                Link link1 = new Link();
                link1.setCategoryId(categoryId);
                linkRepository.save(link1);
                return link1;
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
        if(fileRepositoryDB.existsById(id)&&fileRepositoryDB.findById(id).get().getOwnerId().equals(userservice.getSpecifyUserId())){
            UploadedFile uploadedFile = fileRepositoryDB.findById(id).get();
            uploadedFile.setCategoryId(null);
            fileRepositoryDB.save(uploadedFile);
        }
        else if(linkRepository.existsById(id)&&linkRepository.findById(id).get().getOwnerId().equals(userservice.getSpecifyUserId())){
            Link link = linkRepository.findById(id).get();
            link.setCategoryId(null);
            linkRepository.save(link);
        }
    }
}
