package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean create(Post post) {
        postRepository.createPost(post);
        return true;
    }

    public List<Post> getAll() {
        List<Post> posts = postRepository.getAllPostOrderById();
        return posts;
    }

    public Optional<Post> getById(int id) {
        return postRepository.getPostById(id);
    }

    public void update(Post post) {
        postRepository.update(post);
    }

    public List<Post> getAllWithPhoto() {
        return postRepository.getPostsWithPhoto();
    }

    public List<Post> getAllByLastDay() {
        return postRepository.getPostsByDay();
    }

    public List<Post> getAllWithByBrand(String brand) {
        return postRepository.getPostsWithCarBrand(brand);
    }

    public void deletePost(Post post) {
        postRepository.deletePost(post);
    }

    public void completedPost(int id) {
        postRepository.soldCar(id);
    }
}
