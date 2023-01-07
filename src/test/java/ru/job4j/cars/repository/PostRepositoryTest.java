package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    @BeforeEach
    void before() {
        var session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Post")
                .executeUpdate();
        session.createQuery("DELETE FROM Car")
                .executeUpdate();
        session.createQuery("DELETE FROM Engine")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void whenAddPostThenUpdate() {
        var post = Post.builder().text("test").build();
        var crud = new CrudRepository(sf);
        var repository = new PostRepository(crud);
        repository.createPost(post);
        post.setText("update");
        repository.update(post);
        assertThat(repository.getPostById(post.getId()).get()
                .getText())
                .isEqualTo(post.getText());
    }

    @Test
    void whenAddTwoPostThenGetAll() {
        var first = Post.builder().text("first").build();
        var second = Post.builder().text("second").build();
        var crud = new CrudRepository(sf);
        var repository = new PostRepository(crud);
        repository.createPost(first);
        repository.createPost(second);
        assertThat(repository.getAllPostOrderById())
                .hasSize(2)
                .isEqualTo(List.of(first, second));
    }

    @Test
    void whenAddPostsThenGetByDay() {
        var first = Post.builder().created(LocalDateTime.now().minusDays(2)).build();
        var second = Post.builder().created(LocalDateTime.now().minusHours(10)).build();
        var crud = new CrudRepository(sf);
        var repository = new PostRepository(crud);
        repository.createPost(first);
        repository.createPost(second);
        assertThat(repository.getPostsByDay())
                .hasSize(1)
                .contains(second);
    }

    @Test
    void whenAddPostWithPhotoThenGetThisPost() {
        byte[] photo = {65};
        var first = Post.builder().photo(photo).build();
        var second = new Post();
        var crud = new CrudRepository(sf);
        var repository = new PostRepository(crud);
        repository.createPost(first);
        repository.createPost(second);
        assertThat(repository.getPostsWithPhoto())
                .hasSize(1)
                .contains(first);
    }

    @Test
    void whenAddPostsWithCarBrandThenGet() {
        var crud = new CrudRepository(sf);
        var postRepository = new PostRepository(crud);
        var engineRepository = new EngineRepository(crud);
        var carRepository = new CarRepository(crud);
        var engine = Engine.builder().name("mz").build();
        engineRepository.createEngine(engine);
        var car = Car.builder().name("mazda")
                .engine(engine)
                .build();
        var withoutBrand = Car.builder()
                .engine(engine)
                .build();
        carRepository.createCar(car);
        carRepository.createCar(withoutBrand);
        var first = Post.builder().text("test")
                .car(car)
                .build();
        var second = Post.builder().text("Brand")
                .car(withoutBrand)
                .build();
        postRepository.createPost(first);
        postRepository.createPost(second);
        assertThat(postRepository.getPostsWithCarBrand("mazda"))
                .hasSize(1)
                .contains(first);
    }
}