package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.User;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        var session = sf.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        var session = sf.openSession();
        try {
           session.createQuery("UPDATE User SET login = :fLogin, password = :fPass WHERE id = :fId")
                   .setParameter("fLogin", user.getLogin())
                   .setParameter("fPass", user.getPassword())
                   .setParameter("fId", user.getId())
                   .executeUpdate();
           session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        var session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE User u WHERE u.id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        var session = sf.openSession();
        List<User> users = new ArrayList<>();
        try {
            session.beginTransaction();
            users = session.createQuery("FROM User u ORDER BY u.id", User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return users;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        var session = sf.openSession();
        Optional<User> user = Optional.empty();
        try {
            user = session.createQuery("FROM User u WHERE u.id = :fId", User.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return user;
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        var session = sf.openSession();
        List<User> users = new ArrayList<>();
        try {
            session.beginTransaction();
            users = session.createQuery("FROM User u WHERE u.login LIKE :fLogin", User.class)
                    .setParameter("fLogin", key).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return users;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        var session = sf.openSession();
        session.beginTransaction();
        var user = session.createQuery("FROM User WHERE login = :uLogin", User.class)
                .setParameter("uLogin", login)
                .uniqueResultOptional();
        session.getTransaction().commit();;
        session.close();
        return user;
    }
}

