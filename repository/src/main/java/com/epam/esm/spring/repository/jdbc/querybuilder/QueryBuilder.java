package com.epam.esm.spring.repository.jdbc.querybuilder;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QueryBuilder {
    private static final String UPDATE = "UPDATE gift_certificate SET ";
    private static final String WHERE = " WHERE ";
    private static final String COMMA = ", ";
    private static final String INJECT = "=?";

    public String buildQueryForUpdate(Map<String, Object> dataToBeUpdated) {
        StringBuilder sb = new StringBuilder(UPDATE);
        int count = 1;
        for (Map.Entry<String, Object> entry : dataToBeUpdated.entrySet()) {
            if (count < dataToBeUpdated.size()) {
                sb.append(entry.getKey());
                sb.append(INJECT);
                sb.append(COMMA);
                count++;
            } else {
                sb.append(entry.getKey());
                sb.append(INJECT);
                sb.append(WHERE);
                sb.append(CertificateFieldType.ID.getName());
                sb.append(INJECT);
            }
        }
        return sb.toString();
    }
}
