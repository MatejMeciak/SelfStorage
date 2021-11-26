package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Models.Category;
import com.appslab.CloudService.Services.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@RequestParam String categoryName){
        return categoryService.createCategory(categoryName);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}