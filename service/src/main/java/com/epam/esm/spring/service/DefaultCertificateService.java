package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.CertificateDao;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.service.converter.CertificateToDtoConverter;
import com.epam.esm.spring.service.converter.DtoToCertificateConverter;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.util.CertificateToMapMapper;
import com.epam.esm.spring.service.util.SearchRequestValidator;
import org.apache.commons.collections4.CollectionUtils;
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
    private final SearchRequestValidator searchRequestValidator;

    @Autowired
    public DefaultCertificateService(CertificateDao certificateDao,
                                     TagService tagService,
                                     CertificateToDtoConverter certificateToDtoConverter,
                                     DtoToCertificateConverter dtoToCertificateConverter,
                                     SearchRequestValidator searchRequestValidator) {
        this.certificateDao = certificateDao;
        this.tagService = tagService;
        this.certificateToDtoConverter = certificateToDtoConverter;
        this.dtoToCertificateConverter = dtoToCertificateConverter;
        this.searchRequestValidator = searchRequestValidator;
    }

    @Override
    public List<CertificateDto> findAll() {
        return certificateDao.findAll()
                .stream()
                .map(certificateToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<CertificateDto> findBy(Map<String, String> params) {
        searchRequestValidator.validateRequest(params);

        return certificateDao.findBy(params)
                .stream()
                .map(certificateToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto findById(long id) {
        return certificateToDtoConverter.convert(certificateDao.findById(id)
                .orElseThrow(EntryNotFoundException::new));
    }

    @Transactional
    @Override
    public CertificateDto insert(CertificateDto certificateDto) {
        if (certificateDao.isExist(certificateDto.getName())) {
            throw new EntryAlreadyExistsException();
        }

        processTagList(certificateDto);

        certificateDto.setCreateDate(LocalDateTime.now());
        certificateDto.setLastUpdateDate(LocalDateTime.now());

        Certificate certificate = certificateDao.insert(dtoToCertificateConverter.convert(certificateDto));

        if (CollectionUtils.isNotEmpty(certificate.getTags())) {
            certificateDao.addTagToCertificate(certificate.getTags(), certificate.getId());
        }

        return certificateToDtoConverter.convert(certificate);
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateDto certificateDto) {
        if (!certificateDao.isExist(certificateDto.getId())) {
            throw new EntryNotFoundException();
        }

        processTagList(certificateDto);

        Certificate c = dtoToCertificateConverter.convert(certificateDto);

        if (c.getTags() != null) {
            certificateDao.deleteTagFromCertificate(c.getId());

            if (CollectionUtils.isNotEmpty(c.getTags())) {
                certificateDao.addTagToCertificate(c.getTags(), c.getId());
            }
        }

        if (c.getName() != null || c.getDescription() != null || c.getPrice() != null || c.getDuration() != null) {
            Map<String, Object> data = CertificateToMapMapper.toMap(c);
            certificateDao.update(c.getId(), data);
        }

        return certificateToDtoConverter.convert(certificateDao.findById(c.getId())
                .orElseThrow(EntryNotFoundException::new));
    }

    @Override
    public void processTagList(CertificateDto certificateDto) {
        if (certificateDto.getTags() != null) {
            for (int i = 0; i < certificateDto.getTags().size(); i++) {
                if (!tagService.isExist(certificateDto.getTags().get(i).getName())) {
                    certificateDto.getTags().set(i, tagService.insert(certificateDto.getTags().get(i)));
                } else {
                    certificateDto.getTags().set(i, tagService.findByName(certificateDto.getTags().get(i).getName()));
                }
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
}
