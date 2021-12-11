package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.Link;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LinkRepository extends CrudRepository<Link,Long> {
    List<Link> findByOwnerId(Long ownerId);

    List<Link> findByLinkNameContainingAndOwnerId(String linkName, Long ownerId);
}
