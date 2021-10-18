package com.epam.esm.spring.repository.jdbc.querybuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CertificateFieldType {
    ID,
    NAME,
    DESCRIPTION,
    PRICE,
    DURATION,
    CREATE_DATE,
    LAST_UPDATE_DATE;

    public static List<String> getNames() {

        return Stream.of(CertificateFieldType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
