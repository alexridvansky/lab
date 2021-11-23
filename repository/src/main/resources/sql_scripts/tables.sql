CREATE TABLE IF NOT EXISTS gift_certificate
(
    id                      BIGINT AUTO_INCREMENT,
    name                    varchar(100) NOT NULL UNIQUE,
    description             VARCHAR(200) NOT NULL,
    price                   DECIMAL NOT NULL,
    duration                INT NOT NULL,
    create_date             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_date        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tag
(
    id                  BIGINT AUTO_INCREMENT,
    name                VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS certificate_tag_xref
(
    certificate_id          BIGINT,
    tag_id                  BIGINT,
    PRIMARY KEY (certificate_id, tag_id),
    FOREIGN KEY (certificate_id)
        REFERENCES gift_certificate(id),
    FOREIGN KEY (tag_id)
        REFERENCES tag(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users
(
    id                  BIGINT AUTO_INCREMENT,
    username            VARCHAR(45) NOT NULL UNIQUE,
    password            VARCHAR(60) NOT NULL,
    firstname           VARCHAR(45),
    lastname            VARCHAR(45),
    role_id             BIGINT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id                  BIGINT AUTO_INCREMENT,
    role                VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id                  BIGINT AUTO_INCREMENT,
    total               DECIMAL NOT NULL,
    purchase_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id             BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
        REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS order_certificate_xref
(
    order_id            BIGINT,
    certificate_id      BIGINT,
    PRIMARY KEY (order_id, certificate_id),
    FOREIGN KEY (order_id)
        REFERENCES orders(id),
    FOREIGN KEY (certificate_id)
        REFERENCES gift_certificate(id)
);