package com.appslab.selfstorage.services.impl;

import com.appslab.selfstorage.models.Link;
import com.appslab.selfstorage.repositories.LinkRepository;
import com.appslab.selfstorage.repositories.UserRepository;
import com.appslab.selfstorage.services.LinkService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    private LinkRepository linkRepository;
    private UserService userService;
    private UserRepository userRepository;

    public LinkServiceImpl(LinkRepository linkRepository, UserService userService, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void saveLink(Link link) {
        Link link1 = new Link();
        link1.setLinkName(link.getLinkName());
        link1.setLink(link.getLink());
        link1.setDate(link.getDate());
        link1.setAccess(link.getAccess());
        link1.setOwnerId(userService.getSpecifyUserId());
        linkRepository.save(link1);
    }

    @Override
    public void deleteLink(Long id) {
        if(userService.getSpecifyUserId().equals(linkRepository.findById(id).get().getOwnerId())){
            linkRepository.deleteById(id);
        }
    }

    @Override
    public Iterable<Link> listOfMyLinks(Long customUserId) {
        return linkRepository.findByOwnerId(customUserId);
    }

    @Override
    public List<Link> findSearchLink(String keyword) {
        return linkRepository.findByLinkNameContainingAndOwnerId(keyword, userService.getSpecifyUserId());
    }

    @Override
    public Link saveEditLink(Link link) {
        Link findedLink = linkRepository.findById(link.getId()).get();
        findedLink.setLinkName(link.getLinkName());
        findedLink.setLink(link.getLink());
        findedLink.setAccess(link.getAccess());

        linkRepository.save(findedLink);
        return findedLink;
    }

    @Override
    public Link getLink(Long id) {
        return linkRepository.findById(id).get();
    }
}
