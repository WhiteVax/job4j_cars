CREATE TABLE IF NOT EXISTS auto_user
(
    id          SERIAL PRIMARY KEY,
    login       VARCHAR,
    password    VARCHAR
);