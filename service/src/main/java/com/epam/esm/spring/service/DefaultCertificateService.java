package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.CertificateDao;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.service.converter.CertificateToDtoConverter;
import com.epam.esm.spring.service.converter.DtoToCertificateConverter;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.exception.EntryNonValidRequestException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.util.CertificateToMapMapper;
import com.epam.esm.spring.service.util.CertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultCertificateService implements CertificateService {
    private final CertificateDao certificateDao;
    private final TagService tagService;
    private final CertificateToDtoConverter certificateToDtoConverter;
    private final DtoToCertificateConverter dtoToCertificateConverter;

    @Autowired
    public DefaultCertificateService(CertificateDao certificateDao,
                                     TagService tagService,
                                     CertificateToDtoConverter certificateToDtoConverter,
                                     DtoToCertificateConverter dtoToCertificateConverter) {
        this.certificateDao = certificateDao;
        this.tagService = tagService;
        this.certificateToDtoConverter = certificateToDtoConverter;
        this.dtoToCertificateConverter = dtoToCertificateConverter;
    }

    @Override
    public List<CertificateDto> findAll() {
        return certificateDao.findAll().stream()
                .map(certificateToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto findById(long id) {
        return certificateToDtoConverter.convert(certificateDao.findById(id).orElseThrow(EntryNotFoundException::new));
    }

    @Transactional
    @Override
    public CertificateDto insert(CertificateDto certificateDto) {
        CertificateValidator.isCertificateValidForInsert(certificateDto);

        checkAndCreateTag(certificateDto);

        certificateDto.setCreateDate(LocalDateTime.now());
        certificateDto.setLastUpdateDate(LocalDateTime.now());

        Certificate certificate = certificateDao.insert(dtoToCertificateConverter.convert(certificateDto));

        if (certificate.getTags() != null && !certificate.getTags().isEmpty()) {
            certificateDao.insertTagIntoXrefTable(certificate.getTags(), certificate.getId());
        }

        return certificateToDtoConverter.convert(certificate);
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateDto certificateDto) {
        CertificateValidator.isCertificateValidForUpdate(certificateDto);

        if (!certificateDao.exists(certificateDto.getId())) {
            throw new EntryNotFoundException();
        }
        CertificateValidator.isCertificateValidForUpdate(certificateDto);
        checkAndCreateTag(certificateDto);

        Certificate c = dtoToCertificateConverter.convert(certificateDto);

        if (c.getTags() != null) {
            if (c.getTags().isEmpty()) {
                certificateDao.detachTagFromXrefTable(c.getId());
            } else {
                certificateDao.insertTagIntoXrefTable(c.getTags(), c.getId());
            }
        }

        if (c.getName() != null || c.getDescription() != null || c.getPrice() != null || c.getDuration() != null) {
            Map<String, Object> data = CertificateToMapMapper.toMap(c);
            certificateDao.update(c.getId(), data);

            return certificateToDtoConverter.convert(certificateDao.findById(c.getId())
                    .orElseThrow(EntryNotFoundException::new));
        } else {
            throw new EntryNonValidRequestException();
        }
    }

    @Override
    public void checkAndCreateTag(CertificateDto certificateDto) {
        for (int i = 0; certificateDto.getTags() != null && i < certificateDto.getTags().size(); i++) {
            if (!tagService.exists(certificateDto.getTags().get(i).getName())) {
                certificateDto.getTags().set(i, tagService.insert(certificateDto.getTags().get(i)));
            } else {
                certificateDto.getTags().set(i, tagService.findByName(certificateDto.getTags().get(i).getName()));
            }
        }
    }

    @Transactional
    @Override
    public CertificateDto deleteById(long id) {
        Certificate certificate = certificateDao.findById(id).orElseThrow(EntryNotFoundException::new);

        certificateDao.deleteById(id);

        return certificateToDtoConverter.convert(certificate);
    }

    @Override
    public boolean exists(String name) {
        return false;
    }
}
