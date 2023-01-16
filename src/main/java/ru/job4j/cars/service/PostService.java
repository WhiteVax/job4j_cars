package ru.job4j.cars.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.EngineRepository;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final EngineRepository engineRepository;
    private final CarRepository carRepository;

    public PostService(PostRepository postRepository, EngineRepository engineRepository, CarRepository carRepository) {
        this.postRepository = postRepository;
        this.engineRepository = engineRepository;
        this.carRepository = carRepository;
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

    public List<Post> getPostsWithPhoto() {
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

    public boolean update(Post post, int engineId, MultipartFile file, Car car) {
        try {
            post.setPhoto(file.getBytes());
            car.setEngine(engineRepository.getByIdEngine(engineId)
                    .orElseThrow(
                            () -> new NoSuchElementException(String.format("Not found engine this id = %s.", engineId))
                    ));
            carRepository.update(car);
            post.setCar(car);
            postRepository.update(post);
        } catch (Exception e) {
            log.error("Error in update.", e);
            return false;
        }
        return true;
    }

    public boolean create(Post post, Car car, MultipartFile file, int engineId) {
        try {
            car.setEngine(engineRepository.getByIdEngine(engineId).orElseThrow(
                    () -> new NoSuchElementException(String.format("Not found engine this id = %s.", engineId)
                    )));
            carRepository.createCar(car);
            post.setCar(car);
            post.setPhoto(file.getBytes());
            postRepository.createPost(post);
        } catch (Exception e) {
            log.error("Error in createPost.", e);
            return false;
        }
        return true;
    }
}
