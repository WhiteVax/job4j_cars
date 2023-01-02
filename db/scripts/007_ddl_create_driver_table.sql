CREATE TABLE IF NOT EXISTS car
(
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    user_id INT REFRENCES auto_user(id)
);