package com.epam.esm.spring.service.util;

import com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType;
import com.epam.esm.spring.service.exception.NotValidSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Stream;

@Component
public class SearchRequestValidator {
    public boolean validateRequest(Map<String, String> params) {
        String sort = params.get("sort").toUpperCase();
        String order = params.get("order").toUpperCase();

        if (StringUtils.isNotEmpty(sort) && CertificateFieldType.getNames().stream().noneMatch(names -> names.equals(sort))) {
            throw new NotValidSearchRequest();
        }

        if (StringUtils.isNotEmpty(order) && Stream.of("ASC", "DESC").noneMatch(value -> value.equals(order))) {
            throw new NotValidSearchRequest();
        }

        return true;
    }
}
