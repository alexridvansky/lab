package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.CertificateDao;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.util.SearchRequestValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultCertificateService implements CertificateService {
    private final CertificateDao certificateDao;
    private final TagService tagService;
    private final SearchRequestValidator searchRequestValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public DefaultCertificateService(CertificateDao certificateDao,
                                     TagService tagService,
                                     SearchRequestValidator searchRequestValidator,
                                     ModelMapper modelMapper) {
        this.certificateDao = certificateDao;
        this.tagService = tagService;
        this.searchRequestValidator = searchRequestValidator;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CertificateDto> findAll() {
        return certificateDao.findAll()
                .stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CertificateDto> findBy(Map<String, String> params) {
        searchRequestValidator.validateRequest(params);

        return certificateDao.findBy(params)
                .stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto findById(long id) {
        return modelMapper.map(certificateDao.findById(id)
                .orElseThrow(EntryNotFoundException::new), CertificateDto.class);
    }

    @Transactional
    @Override
    public CertificateDto insert(CertificateDto certificateDto) {
        if (certificateDao.isExist(certificateDto.getName())) {
            throw new EntryAlreadyExistsException();
        }

        Certificate certificate = modelMapper.map(certificateDto, Certificate.class);

        if (CollectionUtils.isNotEmpty(certificate.getTags())) {
            List<Tag> processedTags = tagService.processTagList(certificate.getTags());
            certificate.setTags(processedTags);
        }

        certificate = certificateDao.insert(certificate);

        return modelMapper.map(certificate, CertificateDto.class);
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateDto certificateDto) {
        Certificate certificate = certificateDao.findById(certificateDto.getId())
                .orElseThrow(EntryNotFoundException::new);

        if (certificateDto.getTags() != null) {
            List<Tag> processedTags = tagService.processTagList(certificateDto.getTags().stream()
                    .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                    .collect(Collectors.toList()));
            certificate.setTags(processedTags);
        }

        if (certificateDto.getName() != null) {
            certificate.setName(certificateDto.getName());
        }

        if (certificateDto.getDescription() != null) {
            certificate.setDescription(certificateDto.getDescription());
        }

        if (certificateDto.getDuration() != null) {
            certificate.setDuration(certificateDto.getDuration());
        }

        if (certificateDto.getPrice() != null) {
            certificate.setPrice(certificateDto.getPrice());
        }

        return modelMapper.map(certificateDao.update(certificate), CertificateDto.class);
    }


    @Transactional
    @Override
    public CertificateDto deleteById(long id) {
        Certificate certificate = certificateDao.findById(id).orElseThrow(EntryNotFoundException::new);
        certificateDao.delete(certificate);

        return modelMapper.map(certificate, CertificateDto.class);
    }
}
