package com.epam.esm.spring.service.util;

import com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType;
import com.epam.esm.spring.service.exception.NotValidSearchRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class SearchRequestValidator {
    public boolean validateRequest(Map<String, String> params) {
        String sort = params.get("sort");
        String order = params.get("order");

        if (!sort.isEmpty() && CertificateFieldType.names().stream().noneMatch(names -> names.equals(sort))) {
            throw new NotValidSearchRequest();
        }

        if (!order.isEmpty() && Arrays.asList("ASC", "DESC").stream().noneMatch(value -> value.equals(order))) {
            throw new NotValidSearchRequest();
        }

        return true;
    }
}
