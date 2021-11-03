package com.epam.esm.spring.service.util;

import com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType;
import com.epam.esm.spring.repository.jdbc.querybuilder.SortingOrderType;
import com.epam.esm.spring.service.dto.CertificateParamDto;
import com.epam.esm.spring.service.exception.NotValidSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SearchRequestValidator {

    public void validateRequest(CertificateParamDto paramDto) {

        if (StringUtils.isNotEmpty(paramDto.getSort()) && CertificateFieldType.getNames()
                .stream().noneMatch(names -> names.equals(paramDto.getSort()))) {
            throw new NotValidSearchRequest(paramDto.getSort());
        }

        if (StringUtils.isNotEmpty(paramDto.getOrder()) && SortingOrderType.getNames()
                .stream().noneMatch(value -> value.equals(paramDto.getOrder().toUpperCase()))) {
            throw new NotValidSearchRequest(paramDto.getOrder());
        }
    }
}