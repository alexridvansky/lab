package com.epam.esm.spring.repository.jdbc.querybuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SortingOrderType {
    ASC,
    DESC;

    public static List<String> getNames() {

        return Stream.of(SortingOrderType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
