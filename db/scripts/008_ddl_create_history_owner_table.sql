CREATE TABLE IF NOT EXISTS history_owner
(
    id SERIAL PRIMARY KEY,
    car_id INT REFERENCES car(id),
    driver_id INT REFERENCES driver(id)
);

COMMENT ON TABLE history_owner IS 'Владельцы машин'