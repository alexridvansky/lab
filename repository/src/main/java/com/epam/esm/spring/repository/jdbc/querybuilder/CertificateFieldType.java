package com.epam.esm.spring.repository.jdbc.querybuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CertificateFieldType {
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE("price"),
    DURATION("duration"),
    CREATE_DATE("createDate"),
    LAST_UPDATE_DATE("lastUpdateDate");

    private final String fieldName;

    public static List<String> getNames() {

        return Stream.of(CertificateFieldType.values())
                .map(CertificateFieldType::getFieldName)
                .collect(Collectors.toList());
    }
}
