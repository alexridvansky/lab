package com.epam.esm.spring.repository.jdbc.mapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ColumnName {
    // Tag table columns' names
    public static final String TAG_ID = "t_id";
    public static final String TAG_NAME = "t_name";

    // Certificate table columns' names
    public static final String CERTIFICATE_ID = "id";
    public static final String CERTIFICATE_NAME = "name";
    public static final String CERTIFICATE_DESCRIPTION = "description";
    public static final String CERTIFICATE_PRICE = "price";
    public static final String CERTIFICATE_DURATION = "duration";
    public static final String CERTIFICATE_CREATE_DATE = "create_date";
    public static final String CERTIFICATE_LAST_UPDATE_DATE = "last_update_date";
    public static final String CERTIFICATE_TAG_NAME = "t_name";

}
