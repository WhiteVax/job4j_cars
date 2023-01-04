CREATE TABLE IF NOT EXISTS auto_user
(
    id          SERIAL PRIMARY KEY,
    login       VARCHAR,
    password    VARCHAR
);

COMMENT ON TABLE auto_user IS 'Пользователи'