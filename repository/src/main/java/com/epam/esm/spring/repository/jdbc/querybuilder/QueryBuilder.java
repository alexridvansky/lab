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
    private static final String ASC = "asc";
    private static final String SEARCH = "WHERE t.name like ('%%' %s '%%') and (gc.name LIKE concat ('%%', '%s', '%%') " +
            "or gc.description LIKE concat ('%%', '%s', '%%')) ";
    private static final String TAG_LIKE = " t.name like ('%%' %s '%%') ";
    private static final String AND = " AND ";
    private static final String NAME_DESC_LIKE = " (gc.name LIKE concat ('%%', '%s', '%%') \" +\n" +
            "            \"or gc.description LIKE concat ('%%', '%s', '%%')) ";

    public String buildQueryForUpdate(Map<String, Object> dataToBeUpdated) {
        StringBuilder sb = new StringBuilder(UPDATE);
        int count = 1;
        for (Map.Entry<String, Object> entry : dataToBeUpdated.entrySet()) {
            if (count < dataToBeUpdated.size()) {
                sb.append(entry.getKey()).append(INJECT).append(COMMA);
                count++;
            } else {
                sb.append(entry.getKey()).append(INJECT).append(WHERE).append(CertificateFieldType.ID.getName()).append(INJECT);
            }
        }
        return sb.toString();
    }

    public String buildQueryForSearch(Map<String, String> params) {
        String query = String.format(params.get("query") + SEARCH,"'" + params.get("tag")
                + "'" , params.get("search"), params.get("search"));
        StringBuilder sb = new StringBuilder(query);

        if (params.get("sort") != null && !params.get("sort").isEmpty()) {
            if (params.get("order") != null && !params.get("order").isEmpty()) {
                sb.append(ORDER_BY).append(params.get("sort")).append(SPACE).append(params.get("order"));
            } else {
                sb.append(ORDER_BY).append(params.get("sort")).append(SPACE).append(ASC);
            }
        }

        return sb.toString();
    }
}
