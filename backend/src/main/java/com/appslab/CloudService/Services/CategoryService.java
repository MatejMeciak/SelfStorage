package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getListOfCategories();
    Category getCategory(Long id);
    Category createCategory(String categoryName);
    Object addContentToCategory(Long categoryId, Long requestId);
    void deleteCategory(Long id);
    void deleteContentFromCategory(Long id);
}
