history_owner (car_id, driver_id, startAt, endAt).

CREATE TABLE IF NOT EXISTS history_owner
(
    id SERIAL PRIMARY KEY,
    car_id REFERENCES car(id),
    driver_id REFERENCES driver(id),
    startAt TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    endAt TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);