package com.appslab.selfstorage.controllers;

import com.appslab.selfstorage.models.Link;
import com.appslab.selfstorage.services.LinkService;
import com.appslab.selfstorage.services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/link")
@RestController
public class LinkController {
    private LinkService linkService;
    private UserService userService;

    public LinkController(LinkService linkService, UserService userService) {
        this.linkService = linkService;
        this.userService = userService;
    }

    @PostMapping
    public void uploadLink(@RequestBody Link link){
        linkService.saveLink(link);
    }

    @GetMapping("{id}")
    public Link getLink(@PathVariable Long id){
        return linkService.getLink(id);
    }

    @GetMapping("/allMyLinks")
    public Iterable<Link> getAllMyLinks(){
        return linkService.listOfMyLinks(userService.getSpecifyUserId());
    }

    @GetMapping("/search")
    public List<Link> getSearchLinks(@RequestParam("keyword") String keyword){
        return linkService.findSearchLink(keyword);
    }

    @PutMapping("/edit")
    public Link editLink(@RequestBody Link link){
        return linkService.saveEditLink(link);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLink(@PathVariable Long id){
        linkService.deleteLink(id);
    }
}
