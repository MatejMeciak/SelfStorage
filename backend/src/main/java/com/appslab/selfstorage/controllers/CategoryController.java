package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.dto.CategoryBasicInfo;
import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.UploadedFile;
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

    @GetMapping("/list")
    public List<Category> getCategories(){
        return categoryService.getListOfCategories();
    }

    @GetMapping("/content/files")
    public List<UploadedFile> getFilesInCategory(@RequestParam Long id){
        return categoryService.getFilesInCategory(id);
    }

    @GetMapping("/content/folders")
    public List<Folder> getFoldersInCategory(@RequestParam Long id){
        return categoryService.getFoldersInCategory(id);
    }

    @GetMapping("/{id}")
    public CategoryBasicInfo getCategory(@PathVariable Long id){
        Category category = categoryService.getCategory(id);
        CategoryBasicInfo categoryBasicInfo = new CategoryBasicInfo();
        categoryBasicInfo.setCreatorId(category.getCreatorId());
        categoryBasicInfo.setId(categoryBasicInfo.getId());
        categoryBasicInfo.setName(category.getName());
        return categoryBasicInfo;
    }

    @PostMapping("/{categoryId}/add")
    public Object addContentToCategory(@PathVariable Long categoryId, @RequestParam Long requestId){
        return categoryService.addContentToCategory(categoryId, requestId);
    }

    @PostMapping
    public Category createCategory(@RequestParam String name){
        return categoryService.createCategory(name);
    }

    @DeleteMapping ("/{categoryId}/content")
    public List<Category> deleteContentFromCategory(@PathVariable Long categoryId, @RequestParam Long id){
        return categoryService.deleteContentFromCategory(categoryId, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}