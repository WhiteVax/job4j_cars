package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.DriverRepository;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver add(Driver driver) {
        driverRepository.createDriver(driver);
        return driver;
    }

    public void update(Driver driver) {
        driverRepository.update(driver);
    }
}
