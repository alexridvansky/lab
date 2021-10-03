package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.CertificateDao;
import com.epam.esm.spring.service.converter.CertificateToDtoConverter;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCertificateService implements CertificateService {
    private final CertificateDao certificateDao;
    private final CertificateToDtoConverter toDtoConverter;

    @Autowired
    public DefaultCertificateService(CertificateDao certificateDao,
                                     CertificateToDtoConverter toDtoConverter) {
        this.certificateDao = certificateDao;
        this.toDtoConverter = toDtoConverter;
    }

    @Override
    public List<CertificateDto> findAll() {
        return certificateDao.findAll().stream()
                .map(toDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto findById(long id) throws EntryNotFoundException {
        return null; //todo:
    }

    @Override
    public CertificateDto insert(CertificateDto certificateDto) {
        return null; //todo:
    }
}
