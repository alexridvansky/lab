CREATE TABLE tag
(
    id                  BIGINT AUTO_INCREMENT,
    name                VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);