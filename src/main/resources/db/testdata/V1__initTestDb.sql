DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS users;



CREATE TABLE IF NOT EXISTS users
(
    id       bigserial PRIMARY KEY,
    username varchar(50)          NOT NULL,
    password varchar(80)          NOT NULL,
    enabled  BOOLEAN DEFAULT TRUE NOT NULL,
    email    varchar(50)          NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS roles
(
    id   bigserial PRIMARY KEY,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities
(
    id   bigserial PRIMARY KEY,
    name varchar(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id bigint,
    role_id bigint,
    primary key (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS roles_authorities
(
    authority_id bigint,
    role_id bigint,
    primary key (authority_id, role_id),
    FOREIGN KEY (authority_id) REFERENCES authorities (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
    );

CREATE TABLE IF NOT EXISTS profiles
(
    id        bigserial PRIMARY KEY,
    firstname varchar(100) DEFAULT '',
    surname   varchar(100) DEFAULT '',
    phone     varchar(100) DEFAULT '',
    birthday  date         DEFAULT now(),
    sex       varchar(10)  DEFAULT '',
    city      varchar(255) DEFAULT '',
    user_id   BIGINT REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS categories
(
    id    bigserial PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id          bigserial PRIMARY KEY,
    title       VARCHAR(255) UNIQUE  NOT NULL,
    cost        INTEGER,
    present     BOOLEAN DEFAULT TRUE NOT NULL,
    category_id BIGINT REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id       bigserial PRIMARY KEY,
    date     DATE DEFAULT now(),
    cost     INTEGER,
    receiver VARCHAR(50),
    phone    VARCHAR(50),
    address  VARCHAR(255),
    user_id  bigint REFERENCES users (id)
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




