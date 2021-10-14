package com.epam.esm.spring.service.util;

import com.epam.esm.spring.repository.model.Certificate;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.DESCRIPTION;
import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.DURATION;
import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.LAST_UPDATE_DATE;
import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.NAME;
import static com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType.PRICE;

@UtilityClass
public class CertificateToMapMapper {

    public static Map<String, Object> toMap(Certificate c) {
        Map<String, Object> data = new HashMap<>();
        if (Objects.nonNull(c.getName())) {
            data.put(NAME.toString(), c.getName());
        }
        if (Objects.nonNull(c.getDescription())) {
            data.put(DESCRIPTION.toString(), c.getDescription());
        }
        if (Objects.nonNull(c.getPrice())) {
            data.put(PRICE.toString(), c.getPrice());
        }
        if (Objects.nonNull(c.getDuration())) {
            data.put(DURATION.toString(), c.getDuration());
        }

        data.put(LAST_UPDATE_DATE.toString(), LocalDateTime.now());

        return data;
    }
}
