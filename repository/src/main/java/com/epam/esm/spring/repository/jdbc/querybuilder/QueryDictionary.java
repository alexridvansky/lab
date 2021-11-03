package com.epam.esm.spring.repository.jdbc.querybuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QueryDictionary {
    public static final String WHERE = " WHERE ";
    public static final String COMMA = ", ";
    public static final String INJECT = "=?";
    public static final String SPACE = " ";
    public static final String QUOTE = "'";
    public static final String EMPTY = "";
    public static final String PERCENT = "%";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String ASC = "asc";

    public static final String ID = "id";
    public static final String TAGS = "tags";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
}
