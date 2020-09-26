package ru.ndg.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ndg.blog.models.Post;
import ru.ndg.blog.repositories.PostRepository;

import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Iterable<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post saveOrUpdatePost(Post post) {
        return postRepository.save(post);
    }

    public Post findPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        }
        throw new RuntimeException("Not found post by id " + id);
    }

    public boolean existsPostById(Long id) {
        return postRepository.existsById(id);
    }

    public void removePostById(Long id) {
        postRepository.deleteById(id);
    }
}
