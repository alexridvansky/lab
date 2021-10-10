create table gift_certificate
(
    id                      BIGINT AUTO_INCREMENT,
    name                    varchar(100)  NOT NULL,
    description             VARCHAR(200) NOT NULL,
    price                   DECIMAL NOT NULL,
    duration                INT NOT NULL,
    create_date             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_date        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);