package com.epam.esm.spring.service.util;

import com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType;
import com.epam.esm.spring.service.exception.NotValidSearchRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class SearchRequestValidator {
    public boolean validateRequest(Map<String, String> params) {
        Arrays.stream(CertificateFieldType.values())
                .filter(value -> value.toString().equals(params.get("sort")))
                .findFirst()
                .orElseThrow(NotValidSearchRequest::new);

        Arrays.asList("ASC", "DESC").stream()
                .filter(value -> value.toString().equals(params.get("order")))
                .findFirst()
                .orElseThrow(NotValidSearchRequest::new);

        return true;
    }
}
