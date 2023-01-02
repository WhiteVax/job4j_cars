CREATE TABLE IF NOT EXISTS car
(
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    engine_id INT REFERENCES engine(id)
);