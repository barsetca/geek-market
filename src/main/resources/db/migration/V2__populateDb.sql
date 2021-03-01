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
FROM roles_authorities;
DELETE
FROM roles;
DELETE
FROM authorities;
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
       ('Product2', 90, 2),
       ('Product3', 80, 3),
       ('Product4', 70, 1),
       ('Product5', 60, 2),
       ('Product6', 50, 3),
       ('Product7', 40, 1),
       ('Product8', 30, 2),
       ('ProductNine', 20, 3),
--        ('Product10', 10),
--        ('Product11', 10),
--        ('Product12', 30),
--        ('Product13', 40),
--        ('Product14', 50),
--        ('Product15', 60),
--        ('Product16', 70),
--        ('Product17', 80),
--        ('Product18', 90),
--        ('Product19', 100),
--        ('Product20', 100),
--        ('Product21', 100),
--        ('Product22', 190),
--        ('Product23', 180),
--        ('Product24', 170),
--        ('Product25', 160),
--        ('Product26', 150),
--        ('Product27', 140),
--        ('Product28', 130),
--        ('Product2Nine', 220),
--        ('Product30', 210),
--        ('Product31', 210),
--        ('Product32', 230),
--        ('Product33', 240),
--        ('Product34', 250),
--        ('Product35', 260),
--        ('Product36', 370),
--        ('Product37', 380),
--        ('Product38', 390),
--        ('Product39', 310),
--        ('Product40', 310),
--        ('Product51', 100),
--        ('Product52', 90),
--        ('Product53', 80),
--        ('Product54', 70),
--        ('Product55', 60),
--        ('Product56', 50),
--        ('Product57', 40),
--        ('Product58', 30),
--        ('Product5Nine', 20),
--        ('Product60', 10),
--        ('Product61', 100),
--        ('Product62', 90),
--        ('Product63', 80),
--        ('Product64', 70),
--        ('Product65', 60),
--        ('Product66', 50),
--        ('Product67', 40),
--        ('Product68', 30),
--        ('Product6Nine', 20),
--        ('Product70', 10),
--        ('Product71', 100),
--        ('Product72', 90),
--        ('Product73', 80),
--        ('Product74', 70),
--        ('Product75', 60),
--        ('Product76', 50),
--        ('Product77', 40),
--        ('Product78', 30),
--        ('Product7Nine', 20),
       ('Product80', 150, 1);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_EXCLUSIVE');

INSERT INTO authorities (name)
VALUES ('SUPERUSER'),
       ('READER'),
       ('WRITER'),
       ('EDITOR');

INSERT INTO users (username, password, email)
VALUES ('user', '$2y$12$bA7N3xIrk9E4PtgKJGG9TOagHvouaoUFkiXBsgHtZmUbyUhnQgdHq', 'user@user.ru'),
       ('admin', '$2y$12$plJkmvCZlSLWOLSe7V4rCOOokMSa20cznpdFYaBuPUCY3s6z18Hm.', 'admin@admin.com'),
       ('superadmin', '$2y$12$plJkmvCZlSLWOLSe7V4rCOOokMSa20cznpdFYaBuPUCY3s6z18Hm.', 'super@admin.com');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (3, 3);

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


