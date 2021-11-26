package com.appslab.CloudService.Controllers;

import com.appslab.CloudService.Models.Link;
import com.appslab.CloudService.Services.LinkService;
import com.appslab.CloudService.Services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/link")
@RestController
public class LinkController {
    private LinkService linkService;
    private UserService userService;

    public LinkController(LinkService linkService, UserService userService) {
        this.linkService = linkService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public void uploadLink(@RequestBody Link link){
        linkService.saveLink(link);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLink(@PathVariable Long id){
        linkService.deleteLink(id);
    }

    @GetMapping("/allMyLinks")
    public Iterable<Link> getAllMyLinks(){
        return linkService.listOfMyLinks(userService.getSpecifyUserId());
    }

    @GetMapping("/search")
    public List<Link> getSearchLinks(@Param("keyword") String keyword){
        return linkService.findSearchLink(keyword);
    }

    @PutMapping("/edit")
    public Link editLink(@RequestBody Link link){
        return linkService.saveEditLink(link);
    }
}
