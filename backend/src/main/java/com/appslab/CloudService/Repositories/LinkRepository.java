package com.appslab.CloudService.Repositories;

import com.appslab.CloudService.Models.Link;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LinkRepository extends CrudRepository<Link,Long> {
    List<Link> findByCustomUserId(Long customUserId) ;

    @Query("SELECT u FROM Link u WHERE u.linkName LIKE %?1% AND u.customUserId = ?2")
    List<Link> findByLinkName(String keyword, Long customUserId);
}
