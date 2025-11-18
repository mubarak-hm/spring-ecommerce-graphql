
TRUNCATE TABLE users, categories, products, orders, order_items, reviews RESTART IDENTITY CASCADE;


INSERT INTO users (id, name, email, role, created_at, updated_at) VALUES
                                                                      (1, 'Alice Customer', 'alice@example.com', 'CUSTOMER', NOW(), NOW()),
                                                                      (2, 'Bob Seller', 'bob@example.com', 'SELLER', NOW(), NOW());


INSERT INTO categories (id, name, description) VALUES
                                                   (1, 'Electronics', 'Laptops, phones, and accessories'),
                                                   (2, 'Books', 'Physical and digital books');

INSERT INTO products (id, name, description, price, stock, category_id, seller_id, created_at, updated_at) VALUES
                                                                                                               (1, 'Pro Laptop', 'A high-end laptop for professionals.', 1499.99, 50, 1, 2, NOW(), NOW()),
                                                                                                               (2, 'GraphQL Guide', 'The complete guide to GraphQL.', 45.50, 200, 2, 2, NOW(), NOW()),
                                                                                                               (3, 'Wireless Mouse', 'An ergonomic wireless mouse.', 75.00, 150, 1, 2, NOW(), NOW());

INSERT INTO orders (id, user_id, total, status, payment_method, created_at, updated_at) VALUES
    (1, 1, 1590.99, 'DELIVERED', 'CREDIT_CARD', NOW(), NOW());


INSERT INTO order_items (id, order_id, product_id, quantity, subtotal) VALUES
                                                                           (1, 1, 1, 1, 1499.99);

INSERT INTO orders (id, user_id, total, status, payment_method, created_at, updated_at) VALUES
    (2, 1, 75.00, 'PENDING', 'PAYPAL', NOW(), NOW());

INSERT INTO order_items (id, order_id, product_id, quantity, subtotal) VALUES
    (3, 2, 3, 1, 75.00);


INSERT INTO reviews (id, rating, comment, product_id, user_id, created_at) VALUES
                                                                               (1, 5, 'Absolutely love this laptop! Worth every penny.', 1, 1, NOW()),
                                                                               (2, 4, 'Great book, but a bit dense for beginners.', 2, 1, NOW());

--
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));
SELECT setval('orders_id_seq', (SELECT MAX(id) FROM orders));
SELECT setval('order_items_id_seq', (SELECT MAX(id) FROM order_items));
SELECT setval('reviews_id_seq', (SELECT MAX(id) FROM reviews));