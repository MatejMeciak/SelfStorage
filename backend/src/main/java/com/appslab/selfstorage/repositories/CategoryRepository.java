package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.Category;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    List<Category> findByCreatorId(Long creatorId);

    Category findByName(String name);
}
