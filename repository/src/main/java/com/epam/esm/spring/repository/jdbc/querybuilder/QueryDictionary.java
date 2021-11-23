package com.epam.esm.spring.repository.jdbc.querybuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QueryDictionary {
    public static final String SQL_FIND_MOST_USED_TAG
            = "SELECT tag.id, tag.name FROM tag " +
            "INNER JOIN certificate_tag_xref AS ctx ON ctx.tag_id=tag.id " +
            "INNER JOIN gift_certificate AS gc ON gc.id=ctx.certificate_id " +
            "INNER JOIN order_certificate_xref AS ocx ON ocx.certificate_id=gc.id " +
            "INNER JOIN orders AS o ON o.id=ocx.order_id AND o.user_id=" +
            "   (SELECT user_id FROM orders " +
            "   GROUP BY user_id ORDER BY SUM(orders.total) DESC LIMIT 1) " +
            "GROUP BY tag.id, tag.name ORDER BY COUNT(tag.id) DESC LIMIT 1";

    public static final String EMPTY = "";
    public static final String PERCENT = "%";
    public static final String ASC = "asc";

    public static final String ID = "id";
    public static final String TAGS = "tags";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String DESCRIPTION = "description";
}