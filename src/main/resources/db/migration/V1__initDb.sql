DROP TABLE IF EXISTS products;

CREATE TABLE IF NOT EXISTS products
(
    id    bigserial PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    cost  INTEGER
);

