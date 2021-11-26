package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.Category;

public interface CategoryService {
    Category createCategory(String categoryName);
    void deleteCategory(Long id);
}
