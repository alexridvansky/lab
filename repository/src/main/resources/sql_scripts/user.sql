CREATE TABLE user
(
    id                  BIGINT AUTO_INCREMENT,
    username            VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);