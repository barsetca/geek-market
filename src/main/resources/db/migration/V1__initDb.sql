DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;

CREATE TABLE IF NOT EXISTS products
(
    id    bigserial PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    cost  INTEGER
);

CREATE TABLE IF NOT EXISTS customers
(
    id   bigserial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);


CREATE TABLE IF NOT EXISTS orders
(
    id          bigserial PRIMARY KEY,
    cost        INTEGER,
    customer_id bigint references customers (id)
);

CREATE TABLE IF NOT EXISTS order_items
(
    id         bigserial PRIMARY KEY,
    cost       INTEGER,
    total_cost INTEGER,
    quantity   INTEGER,
    product_id bigint references products (id),
    order_id   bigint references orders (id)
);



