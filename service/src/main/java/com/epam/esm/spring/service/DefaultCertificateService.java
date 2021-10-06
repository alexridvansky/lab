package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.CertificateDao;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.service.converter.CertificateToDtoConverter;
import com.epam.esm.spring.service.converter.DtoToCertificateConverter;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.exception.EntryNonValidDescriptionException;
import com.epam.esm.spring.service.exception.EntryNonValidNameException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCertificateService implements CertificateService {
    private final CertificateDao certificateDao;
    private final TagService tagService;
    private final CertificateToDtoConverter toDtoConverter;
    private final DtoToCertificateConverter toCertificateConverter;

    @Autowired
    public DefaultCertificateService(CertificateDao certificateDao,
                                     TagService tagService,
                                     CertificateToDtoConverter toDtoConverter,
                                     DtoToCertificateConverter toCertificateConverter) {
        this.certificateDao = certificateDao;
        this.tagService = tagService;
        this.toDtoConverter = toDtoConverter;
        this.toCertificateConverter = toCertificateConverter;
    }

    @Override
    public List<CertificateDto> findAll() {
        return certificateDao.findAll().stream()
                .map(toDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto findById(long id) throws EntryNotFoundException {
        return toDtoConverter.convert(certificateDao.findById(id).orElseThrow(EntryNotFoundException::new));
    }

    @Transactional
    @Override
    public CertificateDto insert(CertificateDto certificateDto) {
        if (certificateDto.getName() == null || certificateDto.getName().isEmpty()) {
            throw new EntryNonValidNameException();
        }
        if (certificateDto.getDescription() == null || certificateDto.getDescription().isEmpty()) {
            throw new EntryNonValidDescriptionException();
        }

        for (int i = 0; i < certificateDto.getTags().size(); i++) {
            if (!tagService.isExists(certificateDto.getTags().get(i).getName())) {
                certificateDto.getTags().set(i, tagService.insert(certificateDto.getTags().get(i)));
            } else {
                certificateDto.getTags().set(i, tagService.findByName(certificateDto.getTags().get(i).getName()));
            }
        }

        certificateDto.setCreateDate(LocalDateTime.now());
        certificateDto.setLastUpdateDate(LocalDateTime.now());

        Certificate certificate = certificateDao.insert(toCertificateConverter.convert(certificateDto));
        certificateDao.insertTagIntoXrefTable(certificate.getTags(), certificate.getId());

        return toDtoConverter.convert(certificate);
    }

    @Transactional
    @Override
    public CertificateDto deleteById(long id) {
        Certificate certificate = certificateDao.findById(id).orElseThrow(EntryNotFoundException::new);

        certificateDao.detachTagFromXrefTable(id);
        certificateDao.deleteById(id);

        return toDtoConverter.convert(certificate);
    }

    @Override
    public boolean isExists(String name) {
        return false;
    }
}
