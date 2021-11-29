package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Models.Category;
import com.appslab.CloudService.Models.Link;
import com.appslab.CloudService.Models.UploadedFile;
import com.appslab.CloudService.Services.CategoryService;
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

    @PostMapping("/{id}/add")
    public Object addFileOrLinkToCategory(@PathVariable Long id, @RequestBody(required = false) UploadedFile uploadedFile, @RequestBody(required = false) Link link){
        return categoryService.addFileOrLinkToCategory(id, uploadedFile, link);
    }

    @PostMapping
    public Category createCategory(@RequestParam String categoryName){
        return categoryService.createCategory(categoryName);
    }

    @DeleteMapping("/category/{id}")
    public void deleteFileOrLinkFromCategory(@PathVariable Long id ){

    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }


}