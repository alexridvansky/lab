package com.epam.esm.spring.service.converter;

import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.service.dto.CertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CertificateToDtoConverter implements Converter<Certificate, CertificateDto> {
    private final TagToDtoConverter tagToDtoConverter;

    @Autowired
    public CertificateToDtoConverter(TagToDtoConverter tagToDtoConverter) {
        this.tagToDtoConverter = tagToDtoConverter;
    }

    @Override
    public CertificateDto convert(Certificate source) {
        return CertificateDto.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .duration(source.getDuration())
                .createDate(source.getCreateDate())
                .lastUpdateDate(source.getLastUpdateDate())
                .tags(source.getTags() != null ?
                        source.getTags().stream()
                                .map(tagToDtoConverter::convert)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }
}
