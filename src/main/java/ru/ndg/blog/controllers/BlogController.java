package ru.ndg.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ndg.blog.models.Post;
import ru.ndg.blog.services.PostService;

@Controller
public class BlogController {

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd() {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    // or @ModelAttribute
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String fullText) {
        Post post = new Post(title, anons, fullText);
        postService.saveOrUpdatePost(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(name = "id") Long id, Model model) {
        if (!postService.existsPostById(id)) {
            return "redirect:/blog";
        }
        Post post = postService.findPostById(id);
        Integer views = post.getViews();
        if (views == null) {
            views = 0;
        }
        post.setViews(++views);
        post = postService.saveOrUpdatePost(post);
        model.addAttribute("post", post);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(name = "id") Long id, Model model) {
        if (!postService.existsPostById(id)) {
            return "redirect:/blog";
        }
        Post post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    // or @ModelAttribute
    public String blogPostUpdate(@RequestParam String title, @RequestParam String anons, @RequestParam String fullText, @PathVariable(name = "id") Long id, Model model) {
        Post post = postService.findPostById(id);
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(fullText);
        postService.saveOrUpdatePost(post);
        model.addAttribute("editablePost", true);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    // or @ModelAttribute
    public String blogPostUpdate(@PathVariable(name = "id") Long id) {
        postService.removePostById(id);
        return "redirect:/blog";
    }
}
