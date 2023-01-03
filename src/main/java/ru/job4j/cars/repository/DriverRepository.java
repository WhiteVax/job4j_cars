package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DriverRepository {
    private final CrudRepository crudRepository;

    public List<Driver> getAllOrderById() {
        return crudRepository.query("From Driver ORDER BY id", Driver.class);
    }

    public Driver createDriver(Driver driver) {
        crudRepository.run(session ->
                session.persist("driver", driver));
        return driver;
    }

    public Optional<Driver> getByIdDriver(int id) {
        return crudRepository.optional("FROM Driver WHERE id = :fId", Driver.class, Map.of("fId", id));
    }
}
