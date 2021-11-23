INSERT INTO tag (id, name)
VALUES (1, 'food'),
       (2, 'bbq'),
       (3, 'beer'),
       (4, 'sauna'),
       (5, 'gym'),
       (6, 'cardio'),
       (7, 'health'),
       (8, 'fitness'),
       (9, 'fishing');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date,last_update_date)
VALUES (1, 'Beef and pork fall weekend party', 'unlimited food for a couple of visitors', 50, 2, '2019-11-17 11:11:11', '2021-10-18 11:11:11'),
       (2, 'Paulaner beer fest', 'including entry tickets and food for group of 5 for weekend', 150, 1, '2019-11-17 11:11:11', '2021-10-18 11:11:11'),
       (3, 'Royal fitness Club "Be in shape" month', 'Unlimited access to all facilities', 100, 30, '2019-11-17 11:11:11', '2021-10-18 11:11:11');

INSERT INTO certificate_tag_xref (certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (2, 1),
       (2, 2),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (3, 8);

INSERT INTO roles (name)
VALUES ('ROLE_ROOT');

INSERT INTO users (username, password, firstname, lastname, role_id, state)
VALUES ('root', '$2a$10$rZ5zSZPzLMKyznWqGB1SSOLFskKuPwF/HSYGioJs4ScU0ayyYlNFO', 'root', 'root', 1, 1),
       ('petja', '$2a$10$PuNVbEHw23jZLcYngF6DM.Mho.vWZ7nV2wDx0SFD89vqXZ2Smwwu.', 'petja', 'petja', 2, 1);