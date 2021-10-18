package com.epam.esm.spring.repository.jdbc.dao;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TagSqlQuery {
    public static final String SQL_INSERT = "INSERT INTO tag (name) VALUES (?)";
    public static final String SQL_FIND_ALL = "SELECT id AS t_id, name AS t_name FROM tag";
    public static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = ?";
    public static final String SQL_FIND_BY_NAME = SQL_FIND_ALL + " WHERE name = ?";
    public static final String SQL_ORDER_BY_ID = " ORDER BY id";
    public static final String SQL_COUNT = "SELECT count(*) FROM tag";
    public static final String SQL_COUNT_BY_NAME = SQL_COUNT + " WHERE name = ?";
    public static final String SQL_COUNT_BY_ID = SQL_COUNT + " WHERE id = ?";
    public static final String SQL_COUNT_IN_CROSS_TABLE = "SELECT count(*) FROM certificate_tag_xref WHERE tag_id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE FROM tag WHERE id = ?";
}
