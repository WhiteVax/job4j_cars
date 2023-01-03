package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {
    private final CrudRepository crudRepository;

    public List<Car> getAllOrderById() {
        return crudRepository.query("From Car ORDER BY id", Car.class);
    }

    public Car createTask(Car car) {
        crudRepository.run(session ->
                session.persist("task", car));
        return car;
    }

    public Optional<Car> getByIdCar(int id) {
        return crudRepository.optional("FROM Car WHERE id = :fId", Car.class, Map.of("fId", id));
    }
}
