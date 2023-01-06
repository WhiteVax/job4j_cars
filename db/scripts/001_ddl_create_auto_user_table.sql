CREATE TABLE IF NOT EXISTS auto_user
(
    id          SERIAL PRIMARY KEY,
    login       VARCHAR UNIQUE NOT NULL,
    password    VARCHAR NOT NULL
);

COMMENT ON TABLE auto_user IS 'Пользователи'