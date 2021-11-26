package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.Models.Category;
import com.appslab.CloudService.Repositories.CategoryRepository;
import com.appslab.CloudService.Services.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String categoryName){
        Category category = new Category();
        category.setName(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id).get();
        categoryRepository.delete(category);
    }
}
