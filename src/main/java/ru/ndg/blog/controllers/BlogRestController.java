package ru.ndg.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ndg.blog.models.Post;
import ru.ndg.blog.services.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BlogRestController {

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/blog")
    public List<Post> getAllPosts() {
        return (List<Post>) postService.findAllPosts();
    }

    @GetMapping("/blog/{id}")
    public Post getPostById(@PathVariable(name = "id") Long id) {
        Post post = postService.findPostById(id);
        if (post == null) {
            throw new RuntimeException("Post not found by id: " + id);
        }
        return post;
    }
}
