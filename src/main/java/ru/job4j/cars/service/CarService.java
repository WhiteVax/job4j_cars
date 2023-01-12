package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.Optional;
@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car add(Car car) {
        carRepository.createCar(car);
        return car;
    }

    public Optional<Car> getById(int id) {
        return carRepository.getByIdCar(id);
    }

    public void update(Car car) {
        carRepository.update(car);
    }
}
