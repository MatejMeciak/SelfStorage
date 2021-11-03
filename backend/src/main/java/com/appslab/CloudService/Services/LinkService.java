package com.appslab.CloudService.Services;

import com.appslab.CloudService.Models.Link;

import java.util.List;

public interface LinkService {
    void saveLink(Link link);

    void deleteLink(Long id);

    Iterable<Link> listOfMyLinks(Long customUserId);

    List<Link> findSearchLink(String keyword);

    Link saveEditLink(Link link);
}
