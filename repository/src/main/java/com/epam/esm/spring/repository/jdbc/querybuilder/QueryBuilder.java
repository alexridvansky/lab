package com.epam.esm.spring.repository.jdbc.querybuilder;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QueryBuilder {
    private static final String UPDATE = "UPDATE gift_certificate SET ";
    private static final String WHERE = " WHERE ";
    private static final String COMMA = ", ";
    private static final String INJECT = "=?";
    private static final String SPACE = " ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String SEARCH = "WHERE t.name like ('%%' %s '%%') and (gc.name LIKE concat ('%%', '%s', '%%') " +
            "or gc.description LIKE concat ('%%', '%s', '%%')) ";

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

    public String buildQueryForSearch(Map<String, String> params) {
        String query = String.format(params.get("query") + SEARCH,"'" + params.get("tag")
                + "'" , params.get("search"), params.get("search"));
        StringBuilder sb = new StringBuilder(query);

        if (!params.get("sort").isEmpty()) {
            sb.append(ORDER_BY).append(params.get("sort")).append(SPACE).append(params.get("order"));
        }

        return sb.toString();
    }
}
