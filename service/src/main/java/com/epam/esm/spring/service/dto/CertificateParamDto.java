package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CertificateParamDto {

    private static final String DEFAULT_SEARCH = "";
    private static final String DEFAULT_SORT_FIELD = "id";
    private static final String DEFAULT_SORT_ORDER = "asc";

    private Set<String> tags = new HashSet<>();

    private String search = DEFAULT_SEARCH;

    private String sort = DEFAULT_SORT_FIELD;

    private String order = DEFAULT_SORT_ORDER;

    public boolean isRequestMeaningless() {
        return tags.isEmpty() && search.equals(DEFAULT_SEARCH) && sort.equalsIgnoreCase(DEFAULT_SORT_FIELD)
                && order.equalsIgnoreCase(DEFAULT_SORT_ORDER);
    }
}