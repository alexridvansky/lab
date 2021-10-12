package com.epam.esm.spring.repository.jdbc.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.ASC;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.COMMA;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.INJECT;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.ORDER_BY;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.QUOTE;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.SEARCH_QUERY;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.SPACE;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.UPDATE;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.WHERE;
import static com.epam.esm.spring.repository.jdbc.querybuilder.SearchRequestDictionary.ORDER;
import static com.epam.esm.spring.repository.jdbc.querybuilder.SearchRequestDictionary.QUERY;
import static com.epam.esm.spring.repository.jdbc.querybuilder.SearchRequestDictionary.SEARCH;
import static com.epam.esm.spring.repository.jdbc.querybuilder.SearchRequestDictionary.SORT;
import static com.epam.esm.spring.repository.jdbc.querybuilder.SearchRequestDictionary.TAG;

@Component
public class QueryBuilder {

    public String buildQueryForUpdate(Map<String, Object> dataToBeUpdated) {
        StringBuilder sb = new StringBuilder(UPDATE);
        int count = 1;
        for (Map.Entry<String, Object> entry : dataToBeUpdated.entrySet()) {
            if (count < dataToBeUpdated.size()) {
                sb.append(entry.getKey()).append(INJECT).append(COMMA);
                count++;
            } else {
                sb.append(entry.getKey()).append(INJECT).append(WHERE)
                        .append(CertificateFieldType.ID.getName()).append(INJECT);
            }
        }
        return sb.toString();
    }

    public String buildQueryForSearch(Map<String, String> params) {
        String query = String.format(params.get(QUERY) + SEARCH_QUERY,QUOTE + params.get(TAG)
                + QUOTE , params.get(SEARCH), params.get(SEARCH_QUERY));
        StringBuilder sb = new StringBuilder(query);

        if (StringUtils.isEmpty(params.get(SORT))) {
            if (StringUtils.isEmpty(params.get(ORDER))) {
                sb.append(ORDER_BY).append(params.get(SORT)).append(SPACE).append(params.get(ORDER));
            } else {
                sb.append(ORDER_BY).append(params.get(SORT)).append(SPACE).append(ASC);
            }
        }

        return sb.toString();
    }
}
