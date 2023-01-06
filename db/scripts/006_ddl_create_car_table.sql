CREATE TABLE IF NOT EXISTS car
(
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    engine_id INT NOT NULL UNIQUE REFERENCES engine(id)
);

COMMENT ON TABLE car IS 'Машины';
COMMENT ON COLUMN car.name IS 'Марка машины'