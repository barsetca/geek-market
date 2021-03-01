DELETE
FROM order_items;
DELETE
FROM orders;
DELETE
FROM products;
DELETE
FROM categories;
DELETE
FROM users_roles;
DELETE
FROM roles;
DELETE
FROM profiles;
DELETE
FROM users;


INSERT INTO categories (title)
VALUES ('Category1'),
       ('Category2'),
       ('Category3');

INSERT INTO products (title, cost, category_id)
VALUES ('Product1', 100, 1),
       ('Product2', 100, 2),
       ('Product3', 100, 3);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('EXCLUSIVE');

INSERT INTO authorities (name)
VALUES ('SUPERUSER'),
       ('READER'),
       ('WRITER'),
       ('EDITOR');

INSERT INTO users (username, password, email)
VALUES ('user', '$2y$12$bA7N3xIrk9E4PtgKJGG9TOagHvouaoUFkiXBsgHtZmUbyUhnQgdHq', 'user@user.ru'),
       ('admin', '$2y$12$plJkmvCZlSLWOLSe7V4rCOOokMSa20cznpdFYaBuPUCY3s6z18Hm.', 'admin@admin.com');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);

INSERT INTO roles_authorities (role_id, authority_id)
VALUES (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4);

INSERT INTO profiles (firstname, surname, phone, sex, city, user_id)
VALUES ('Ivan', 'Ivanov', '+7(921)999-99-99', 'male', 'Ivangorod', 1),
       ('Ivanna', 'Ivanova', '+7(921)111-11-11', 'famale', 'Ivangorod', 2);


INSERT INTO orders (date, cost, receiver, phone, address, user_id)
VALUES ('2020-11-20', 300, 'receiver', '1234567', 'address', 1);


INSERT INTO order_items (cost, total_cost, quantity, product_id, order_id)
VALUES (100,100,1,1,1),
       (100,100,1,2,1),
       (100,100,1,3,1);
