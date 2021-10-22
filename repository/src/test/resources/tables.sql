CREATE TABLE gift_certificate
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

CREATE TABLE tag
(
    id                  BIGINT AUTO_INCREMENT,
    name                VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE certificate_tag_xref
(
    certificate_id          BIGINT,
    tag_id                  BIGINT,
    PRIMARY KEY (certificate_id, tag_id),
    FOREIGN KEY (certificate_id)
        REFERENCES gift_certificate(id)
        ON DELETE CASCADE,
    FOREIGN KEY (tag_id)
        REFERENCES tag(id)
        ON DELETE CASCADE
);

CREATE TABLE user
(
    id                  BIGINT AUTO_INCREMENT,
    username            VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);