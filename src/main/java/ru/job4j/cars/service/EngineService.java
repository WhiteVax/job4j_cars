package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EngineService {
    private final EngineRepository engineRepository;

    public EngineService(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    public Engine add(Engine engine) {
        engineRepository.createEngine(engine);
        return engine;
    }

    public List<Engine> getAll() {
        return engineRepository.getAllOrderById();
    }

    public Optional<Engine> getById(int id) {
        return engineRepository.getByIdEngine(id);
    }
}
