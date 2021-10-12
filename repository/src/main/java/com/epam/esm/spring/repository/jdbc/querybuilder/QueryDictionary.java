package com.epam.esm.spring.repository.jdbc.querybuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QueryDictionary {
    public static final String UPDATE = "UPDATE gift_certificate SET ";
    public static final String WHERE = " WHERE ";
    public static final String COMMA = ", ";
    public static final String INJECT = "=?";
    public static final String SPACE = " ";
    public static final String QUOTE = "'";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String ASC = "asc";
    public static final String SEARCH_QUERY = "WHERE t.name like ('%%' %s '%%') and (gc.name LIKE concat ('%%', '%s', '%%') " +
            "or gc.description LIKE concat ('%%', '%s', '%%')) ";
}
