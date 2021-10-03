package com.epam.esm.spring.service.converter;

import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.service.dto.CertificateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CertificateToDtoConverter implements Converter<Certificate, CertificateDto> {
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
                .tags(source.getTags())
                .build();
    }
}
