package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    public List<Engine> getAllOrderById() {
        return crudRepository.query("From Engine ORDER BY id", Engine.class);
    }

    public Engine createEngine(Engine engine) {
        crudRepository.run(session ->
                session.persist("engine", engine));
        return engine;
    }

    public Optional<Engine> getByIdEngine(int id) {
        return crudRepository.optional("FROM Engine WHERE id = :fId", Engine.class, Map.of("fId", id));
    }
}
