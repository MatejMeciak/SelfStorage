package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    List<Category> findByCreatorId(Long creatorId);
}
