package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.web.config.ConfigProperties;
import com.epam.esm.spring.web.controller.CertificateController;
import org.springframework.stereotype.Component;

@Component
public class CertificateLinkBuilder extends AbstractLinkBuilder<CertificateDto> {

    public CertificateLinkBuilder(ConfigProperties properties) {
        super(properties);
    }

    @Override
    public CertificateDto addRemoveLink(CertificateDto certificateDto) {
        return addRemoveLinks(certificateDto, CertificateController.class);
    }

    @Override
    public CertificateDto addFindAllLink(CertificateDto certificateDto) {
        return addFindAllLink(certificateDto, CertificateController.class);
    }

    @Override
    public CertificateDto addFindByIdLink(CertificateDto certificateDto) {
        return addFindByIdLink(certificateDto, CertificateController.class);
    }
}
