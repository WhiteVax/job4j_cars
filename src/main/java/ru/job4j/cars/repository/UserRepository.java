package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public Optional<User> create(User user) {
        try {
            crudRepository.run(session -> session.saveOrUpdate(user));
            return Optional.of(user);
        } catch (Exception e) {
               log.error("Error int createUser.", e);
               return Optional.empty();
        }
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        crudRepository.run(
                "DELETE FROM User WHERE id = :fId",
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query("FROM User ORDER BY id ASC", User.class);
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "FROM User WHERE id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "FROM User WHERE login LIKE :fKey", User.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "FROM User WHERE login = :fLogin", User.class,
                Map.of("fLogin", login)
        );
    }


    /**
     * Найти пользователя по login и password.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLoginAndPassword(String login, String password) {
        try {
            return crudRepository.optional(
                    "FROM User WHERE login = :fLogin AND password = :fPass", User.class,
                    Map.of("fLogin", login, "fPass", password)
            );
        } catch (Exception e) {
            log.error("Error in findByLoginAndPassword.", e);
            return Optional.empty();
        }
    }
}

