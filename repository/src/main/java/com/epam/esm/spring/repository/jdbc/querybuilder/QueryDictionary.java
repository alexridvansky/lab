package com.epam.esm.spring.repository.jdbc.querybuilder;

public interface QueryDictionary {
    String UPDATE = "UPDATE gift_certificate SET ";
    String WHERE = " WHERE ";
    String COMMA = ", ";
    String INJECT = "=?";
    String SPACE = " ";
    String QUOTE = "'";
    String ORDER_BY = " ORDER BY ";
    String ASC = "asc";
    String SEARCH_QUERY = "WHERE t.name like ('%%' %s '%%') and (gc.name LIKE concat ('%%', '%s', '%%') " +
            "or gc.description LIKE concat ('%%', '%s', '%%')) ";
}
