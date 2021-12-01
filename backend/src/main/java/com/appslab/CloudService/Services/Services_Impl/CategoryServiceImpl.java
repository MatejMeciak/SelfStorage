package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.Models.Category;
import com.appslab.CloudService.Models.Link;
import com.appslab.CloudService.Models.UploadedFile;
import com.appslab.CloudService.Repositories.CategoryRepository;
import com.appslab.CloudService.Repositories.FileRepositoryDB;
import com.appslab.CloudService.Repositories.LinkRepository;
import com.appslab.CloudService.Services.CategoryService;
import com.appslab.CloudService.Services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    UserService userService;
    FileRepositoryDB fileRepositoryDB;
    LinkRepository linkRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserService userService, FileRepositoryDB fileRepositoryDB, LinkRepository linkRepository) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.fileRepositoryDB = fileRepositoryDB;
        this.linkRepository = linkRepository;
    }

    @Override
    public List<Category> getListOfCategories() {
        return categoryRepository.findByCreatorId(userService.getSpecifyUserId());
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category createCategory(String categoryName){
        Category category = new Category();
        category.setCreatorId(userService.getSpecifyUserId());
        category.setName(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public Object addContentToCategory(Long categoryId, Long requestId) {
        Category category = categoryRepository.findById(categoryId).get();
        if (category.getCreatorId().equals(userService.getSpecifyUserId())){
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
        if (category.getCreatorId().equals(userService.getSpecifyUserId())) {
            categoryRepository.delete(category);
        }
    }

    @Override
    public void deleteContentFromCategory(Long id) {
        if(fileRepositoryDB.existsById(id)){
            UploadedFile uploadedFile = fileRepositoryDB.findById(id).get();
            uploadedFile.setCategoryId(null);
        }
        else if(linkRepository.existsById(id)){
            Link link = linkRepository.findById(id).get();
            
        }
    }
}
