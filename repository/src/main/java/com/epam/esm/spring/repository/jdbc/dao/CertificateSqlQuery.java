package com.epam.esm.spring.repository.jdbc.dao;

public class CertificateSqlQuery {
    public static final String SQL_ORDER_BY_ID = "ORDER BY id";
    public static final String SQL_FIND_ALL =
            "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, " +
                    "t.id AS t_id, t.name AS t_name " +
                    "FROM gift_certificate AS gc " +
                    "LEFT JOIN certificate_tag_xref AS ctx on gc.id = ctx.certificate_id " +
                    "LEFT JOIN tag t on ctx.tag_id = t.id ";
    public static final String SQL_FIND_BY_ID = SQL_FIND_ALL + "WHERE gc.id = ?";
    public static final String SQL_INSERT = "INSERT INTO gift_certificate (name, description, price, duration) " +
            "VALUES (?, ?, ?, ?)";
    public static final String SQL_TAG_ATTACH = "INSERT INTO certificate_tag_xref (certificate_id, tag_id) " +
            "VALUES (?, ?)";
    public static final String SQL_TAG_DETACH = "DELETE FROM certificate_tag_xref WHERE certificate_id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE FROM gift_certificate WHERE id = ?";
    public static final String SQL_COUNT = "SELECT count(*) FROM gift_certificate ";
    public static final String SQL_COUNT_BY_ID = SQL_COUNT + "WHERE id = ?";
    public static final int EXACT_ONE = 1;
    public static final int MORE_THAN_NOTHING = 0;
}
