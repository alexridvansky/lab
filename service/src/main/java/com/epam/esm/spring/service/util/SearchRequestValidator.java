package com.epam.esm.spring.service.util;

import com.epam.esm.spring.repository.jdbc.querybuilder.CertificateFieldType;
import com.epam.esm.spring.repository.model.CertificateParam;
import com.epam.esm.spring.service.TagService;
import com.epam.esm.spring.service.dto.CertificateParamDto;
import com.epam.esm.spring.service.exception.NotValidSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class SearchRequestValidator {

    public void validateRequest(CertificateParamDto paramDto) {

        if (StringUtils.isNotEmpty(paramDto.getSort()) && CertificateFieldType.getNames()
                .stream().noneMatch(names -> names.equalsIgnoreCase(paramDto.getSort()))) {
            throw new NotValidSearchRequest(paramDto.getSort());
        }

        if (StringUtils.isNotEmpty(paramDto.getOrder()) && Stream.of("ASC", "DESC").noneMatch(value ->
                value.equals(paramDto.getOrder().toUpperCase()))) {
            throw new NotValidSearchRequest(paramDto.getOrder());
        }
    }
}
