DELETE
FROM order_items;
DELETE
FROM orders;
DELETE
FROM products;
DELETE
FROM customers;

INSERT INTO products (title, cost)
VALUES ('Product1', 100),
       ('Product2', 90),
       ('Product3', 80),
       ('Product4', 70),
       ('Product5', 60),
       ('Product6', 50),
       ('Product7', 40),
       ('Product8', 30),
       ('ProductNine', 20),
       ('Product10', 10),
       ('Product11', 10),
       ('Product12', 30),
       ('Product13', 40),
       ('Product14', 50),
       ('Product15', 60),
       ('Product16', 70),
       ('Product17', 80),
       ('Product18', 90),
       ('Product19', 100),
       ('Product20', 100),
       ('Product21', 100),
       ('Product22', 190),
       ('Product23', 180),
       ('Product24', 170),
       ('Product25', 160),
       ('Product26', 150),
       ('Product27', 140),
       ('Product28', 130),
       ('Product2Nine', 220),
       ('Product30', 210),
       ('Product31', 210),
       ('Product32', 230),
       ('Product33', 240),
       ('Product34', 250),
       ('Product35', 260),
       ('Product36', 370),
       ('Product37', 380),
       ('Product38', 390),
       ('Product39', 310),
       ('Product40', 310),
       ('Product51', 100),
       ('Product52', 90),
       ('Product53', 80),
       ('Product54', 70),
       ('Product55', 60),
       ('Product56', 50),
       ('Product57', 40),
       ('Product58', 30),
       ('Product5Nine', 20),
       ('Product60', 10),
       ('Product61', 100),
       ('Product62', 90),
       ('Product63', 80),
       ('Product64', 70),
       ('Product65', 60),
       ('Product66', 50),
       ('Product67', 40),
       ('Product68', 30),
       ('Product6Nine', 20),
       ('Product70', 10),
       ('Product71', 100),
       ('Product72', 90),
       ('Product73', 80),
       ('Product74', 70),
       ('Product75', 60),
       ('Product76', 50),
       ('Product77', 40),
       ('Product78', 30),
       ('Product7Nine', 20),
       ('Product80', 10);

INSERT INTO customers (name)
VALUES
       ('Joric'),
       ('Doric');


