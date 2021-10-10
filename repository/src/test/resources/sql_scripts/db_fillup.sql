INSERT INTO tags (name)
VALUES ('food'),
       ('bbq'),
       ('beer'),
       ('sauna'),
       ('gym'),
       ('cardio'),
       ('health'),
       ('fitness');

INSERT INTO gift_certificate (name, description, price, duration)
VALUES ('Beef and pork fall weekend party', 'unlimited food for a couple of visitors', 50, 1),
       ('Paulaner beer fest', 'including entry tickets and food for group of 5', 150, 1),
       ('Royal fitness Club "Be in shape" month', 'Unlimited access to all facilities', 100, 30);

INSERT INTO certificate_tag_xref (certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 1),
       (2, 2),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (3, 8);

