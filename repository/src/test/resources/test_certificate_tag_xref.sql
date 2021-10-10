CREATE TABLE certificate_tag_xref
(
    certificate_id          BIGINT,
    tag_id                  BIGINT,
    PRIMARY KEY (certificate_id, tag_id),
    FOREIGN KEY (certificate_id)
        REFERENCES gift_certificate(id)
        ON DELETE CASCADE,
    FOREIGN KEY (tag_id)
        REFERENCES tags(id)
        ON DELETE CASCADE
);