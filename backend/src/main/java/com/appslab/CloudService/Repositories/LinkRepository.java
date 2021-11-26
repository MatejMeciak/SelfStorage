package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.Link;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LinkRepository extends CrudRepository<Link,Long> {
    List<Link> findByOwnerId(Long ownerId);

    @Query("SELECT u FROM Link u WHERE u.linkName LIKE %?1% AND u.ownerId = ?2")
    List<Link> findByLinkName(String keyword, Long ownerId);
}
