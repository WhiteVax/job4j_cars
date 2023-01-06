package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {

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
        session.createQuery("DELETE FROM User")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void whenCreateThenUpdate() {
        var repository = new UserRepository(new CrudRepository(sf));
        var user = User.builder().login("test").password("passs").build();
        repository.create(user);
        user.setLogin("update");
        repository.update(user);
        assertThat(repository.findById(user.getId()))
                .isNotNull();
    }

    @Test
    void whenCreateThenDelete() {
        var repository = new UserRepository(new CrudRepository(sf));
        var user = User.builder().login("test").password("passs").build();
        repository.create(user);
        var id = user.getId();
        repository.delete(user.getId());
        assertThat(repository.findById(id).isEmpty())
                .isTrue();
    }

    @Test
    void whenAddUsersThenGetAll() {
        var repository = new UserRepository(new CrudRepository(sf));
        var first = User.builder().login("test").password("passs").build();
        var second = User.builder().login("test1").password("passs1").build();
        repository.create(first);
        repository.create(second);
        assertThat(repository.findAllOrderById()).hasSize(2);
    }
}