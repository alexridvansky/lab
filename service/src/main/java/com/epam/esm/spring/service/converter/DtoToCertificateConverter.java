package com.epam.esm.spring.service.converter;

import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.service.dto.CertificateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DtoToCertificateConverter implements Converter<CertificateDto, Certificate> {
    private final DtoToTagConverter dtoToTagConverter;

    public DtoToCertificateConverter(DtoToTagConverter dtoToTagConverter) {
        this.dtoToTagConverter = dtoToTagConverter;
    }

    @Override
    public Certificate convert(CertificateDto source) {
        return Certificate.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .duration(source.getDuration())
                .createDate(source.getCreateDate())
                .lastUpdateDate(source.getLastUpdateDate())
                .tags(source.getTags().stream()
                        .map(dtoToTagConverter::convert)
                        .collect(Collectors.toList()))
                .build();
    }
}
