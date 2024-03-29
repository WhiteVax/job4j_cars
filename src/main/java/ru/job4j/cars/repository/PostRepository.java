package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public Post createPost(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    public void deletePost(Post post) {
        crudRepository.run(session -> session.delete(post));
    }

    public List<Post> getAllPostOrderById() {
        return crudRepository.query("FROM Post Order BY id", Post.class);
    }

    public Optional<Post> getPostById(int id) {
        return crudRepository.optional("FROM Post WHERE id = :fId", Post.class, Map.of("fId", id));
    }

    public void update(Post post) {
        crudRepository.run(session -> session.saveOrUpdate(post));
    }

    public void soldCar(int id) {
        crudRepository.run(
                "UPDATE Post p SET p.status = true Where p.id = :fId",
                Map.of("fId", id));
    }

    public List<Post> getPostsByDay() {
        var now = LocalDateTime.now();
        var minusHours = LocalDateTime.now().minusHours(24);
        return crudRepository.query("FROM Post WHERE created BETWEEN :fMinusHours AND :fNow", Post.class,
                Map.of("fMinusHours", minusHours, "fNow", now));
    }

    public List<Post> getPostsWithPhoto() {
        return crudRepository.query("FROM Post WHERE photo IS NOT NULL", Post.class);
    }

    public List<Post> getPostsWithCarBrand(String brand) {
        return crudRepository.query("FROM Post p JOIN FETCH p.car с WHERE с.name = :fBrand ", Post.class,
                Map.of("fBrand", brand));
    }
}
