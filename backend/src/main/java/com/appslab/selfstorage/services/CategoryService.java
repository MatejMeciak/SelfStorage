package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.UploadedFile;

import java.util.List;

public interface CategoryService {
    List<Category> getListOfCategories();

    List<UploadedFile> getCategory(String name);

    Category createCategory(String categoryName);

    Object addContentToCategory(Long categoryId, Long requestId);

    void deleteCategory(Long id);

    void deleteContentFromCategory(Long id);
}
