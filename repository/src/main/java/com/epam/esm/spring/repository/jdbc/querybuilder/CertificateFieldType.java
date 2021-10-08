package com.epam.esm.spring.repository.jdbc.querybuilder;

import java.util.ArrayList;
import java.util.List;

public enum CertificateFieldType {

    ID("id"),

    NAME("name"),

    DESCRIPTION("description"),

    PRICE("price"),

    DURATION("duration"),

    CREATE_DATE("create_date"),

    LAST_UPDATE_DATE("last_update_date");

    private String name;

    CertificateFieldType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getFields() {
        List<String> fields = new ArrayList<>();
        CertificateFieldType[] values = CertificateFieldType.values();
        for (CertificateFieldType e : values) {
            fields.add(e.getName());
        }
        return fields;
    }
}
