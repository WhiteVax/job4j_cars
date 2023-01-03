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
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    public List<Post> getAllPostOrderById() {
        return crudRepository.query("FROM Post Order BY id", Post.class);
    }

    public Optional<Post> getPostById(int id) {
        return crudRepository.optional("FROM Post WHERE id = :fId", Post.class, Map.of("fId", id));
    }

    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
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

    public List<Post> getPostsWithCarBrand(String like) {
        return crudRepository.query("FROM Post JOIN FETCH Car AS c WHERE c.name LIKE %:fBrand% ", Post.class,
                Map.of("fBrand", like));
    }
}
