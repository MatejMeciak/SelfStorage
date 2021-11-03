package com.appslab.CloudService.Services.Services_Impl;

import com.appslab.CloudService.Models.Link;
import com.appslab.CloudService.Repositories.LinkRepository;
import com.appslab.CloudService.Repositories.UserRepository;
import com.appslab.CloudService.Services.LinkService;
import com.appslab.CloudService.Services.UserService;
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
        link1.setCustomUserId(userService.getSpecifyUserId());
        linkRepository.save(link1);
    }

    @Override
    public void deleteLink(Long id) {
        if(userService.getSpecifyUserId().equals(linkRepository.findById(id).get().getCustomUserId())){
            linkRepository.deleteById(id);
        }
    }

    @Override
    public Iterable<Link> listOfMyLinks(Long customUserId) {
        return linkRepository.findByCustomUserId(customUserId);
    }

    @Override
    public List<Link> findSearchLink(String keyword) {
        return linkRepository.findByLinkName(keyword, userService.getSpecifyUserId());
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
}
