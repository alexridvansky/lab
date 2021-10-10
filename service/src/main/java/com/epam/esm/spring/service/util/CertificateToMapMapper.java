package com.epam.esm.spring.service.util;

import com.epam.esm.spring.repository.model.Certificate;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.DESCRIPTION;
import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.DURATION;
import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.LAST_UPDATE_DATE;
import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.NAME;
import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.PRICE;

@UtilityClass
public class CertificateToMapMapper {

    public static Map<String, Object> toMap(Certificate c) {
        Map<String, Object> data = new HashMap<>();
        if (c.getName() != null) {
            data.put(NAME.getName(), c.getName());
        }
        if (c.getDescription() != null) {
            data.put(DESCRIPTION.getName(), c.getDescription());
        }
        if (c.getPrice() != null) {
            data.put(PRICE.getName(), c.getPrice());
        }
        if (c.getDuration() != null) {
            data.put(DURATION.getName(), c.getDuration());
        }

        data.put(LAST_UPDATE_DATE.getName(), LocalDateTime.now());

        return data;
    }
}