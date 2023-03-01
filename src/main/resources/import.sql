INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (id, email, is_active, secret_key, username) VALUES (0, 'admin@admin.com', true, '$2a$10$pTxGWluhWYgpp4TeaSXn4uvQEXJoLdSkVra3rtSm5ZMUNMP4wGpzW', 'admin');

INSERT INTO user_role (user_id, role_id) VALUES (0, (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));