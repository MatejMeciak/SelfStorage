package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
