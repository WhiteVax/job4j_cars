ALTER TABLE auto_post ADD COLUMN car_id INT REFERENCES car(id);

COMMENT ON COLUMN auto_post.car_id IS 'Колонка для ид машин'