package com.epam.esm.spring.repository.jdbc.querybuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static List<String> getNames() {

        return Stream.of(CertificateFieldType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
