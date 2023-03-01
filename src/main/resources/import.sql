INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (id, email, is_active, secret_key, username) VALUES (0, 'admin@admin.com', true, '$2a$10$pTxGWluhWYgpp4TeaSXn4uvQEXJoLdSkVra3rtSm5ZMUNMP4wGpzW', 'admin');

INSERT INTO user_role (user_id, role_id) VALUES (0, (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));

INSERT INTO currency (id, ticker, currency_type) VALUES (1, 'TON', 'CRYPTO');
INSERT INTO currency (id, ticker, currency_type) VALUES (2, 'USDT', 'CRYPTO');
INSERT INTO currency (id, ticker, currency_type) VALUES (3, 'BTC', 'CRYPTO');
INSERT INTO currency (id, ticker, currency_type) VALUES (4, 'RUB', 'FIAT');

INSERT INTO exchange_rate (first_currency_id, second_currency_id, rate) VALUES (1, 2, 200);
INSERT INTO exchange_rate (first_currency_id, second_currency_id, rate) VALUES (1, 4, 5000);
INSERT INTO exchange_rate (first_currency_id, second_currency_id, rate) VALUES (1, 3, 0.0000321);
