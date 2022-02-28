package com.appslab.selfstorage.services;

import com.appslab.selfstorage.models.Category;
import com.appslab.selfstorage.models.Folder;
import com.appslab.selfstorage.models.File;

import java.util.List;

public interface CategoryService {
    List<Category> getListOfCategories();

    List<File> getFilesInCategory(Long id);

    List<Folder> getFoldersInCategory(Long id);

    Category createCategory(String categoryName);

    Object addContentToCategory(Long categoryId, Long requestId);

    Category deleteCategory(Long id);

    List<Category> deleteContentFromCategory(Long categoryId, Long id);

    Category getCategory(Long id);
}
