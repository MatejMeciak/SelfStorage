package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.services.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/category")
@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories(){
        return categoryService.getListOfCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id){
        return categoryService.getCategory(id);
    }

    @PostMapping("/{categoryId}/add")
    public Object addFileOrLinkToCategory(@PathVariable Long categoryId, @RequestParam Long requestId){
        return categoryService.addContentToCategory(categoryId, requestId);
    }

    @PostMapping
    public Category createCategory(@RequestParam String categoryName){
        return categoryService.createCategory(categoryName);
    }

    @DeleteMapping("/content/{id}")
    public void deleteContentFromCategory(@PathVariable Long id){
        categoryService.deleteContentFromCategory(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}