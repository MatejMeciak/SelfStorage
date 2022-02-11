package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.UploadedFile;

import java.util.List;

public interface CategoryService {
    List<Category> getListOfCategories();

    List<Object> getCategoryContent(String name);

    Category createCategory(String categoryName);

    Object addContentToCategory(Long categoryId, Long requestId);

    void deleteCategory(Long id);

    List<Category> deleteContentFromCategory(Long categoryId, Long id);

    Category getCategory(Long id);
}
