package com.epam.esm.spring.repository.jdbc.mapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ColumnName {
    // Tag table columns' names
    public static final String T_ID = "t_id";
    public static final String T_NAME = "t_name";

    // Certificate table columns' names
    public static final String C_ID = "id";
    public static final String C_NAME = "name";
    public static final String C_DESCRIPTION = "description";
    public static final String C_PRICE = "price";
    public static final String C_DURATION = "duration";
    public static final String C_CREATE_DATE = "create_date";
    public static final String C_LAST_UPDATE_DATE = "last_update_date";
    public static final String C_TAG_NAME = "t_name";

}
