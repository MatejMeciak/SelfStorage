package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.Category;
import com.appslab.CloudService.Models.Link;
import com.appslab.CloudService.Models.UploadedFile;

import java.util.List;

public interface CategoryService {
    List<Category> getListOfCategories();
    Category getCategory(Long id);
    Category createCategory(String categoryName);
    Object addFileOrLinkToCategory(Long categoryId, UploadedFile uploadedFile, Link link);
    void deleteCategory(Long id);
}
