package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.CertificateDao;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.CertificateParam;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateParamDto;
import com.epam.esm.spring.service.dto.CertificateUpdateDto;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.service.exception.EntityIntersectionException;
import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.exception.SubEntryAlreadyAttachedException;
import com.epam.esm.spring.service.exception.SubEntryNotFoundException;
import com.epam.esm.spring.service.util.SearchRequestValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_CERTIFICATE_NOT_FOUND;
import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_TAG_NOT_FOUND;

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
    public List<CertificateDto> findBy(CertificateParamDto paramDto) {
        CertificateParam param = searchRequestValidator.validateRequest(paramDto);

        return certificateDao.findBy(param)
                .stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto findById(Long id) {
        return modelMapper.map(certificateDao.findById(id)
                        .orElseThrow(() -> new EntryNotFoundException(ERROR_CERTIFICATE_NOT_FOUND, id.toString())),
                CertificateDto.class);
    }

    @Transactional
    @Override
    public CertificateDto insert(CertificateDto certificateDto) {
        if (certificateDao.isExist(certificateDto.getName())) {
            throw new EntryAlreadyExistsException(certificateDto.getName());
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
        Certificate originalCertificate = certificateDao.findById(certificateDto.getId())
                .orElseThrow(() -> new EntryNotFoundException(ERROR_CERTIFICATE_NOT_FOUND,
                        certificateDto.getId().toString()));

        if (certificateDao.isExist(certificateDto.getName()) &&
                !certificateDto.getName().equals(originalCertificate.getName())) {
            throw new EntryAlreadyExistsException(certificateDto.getName());
        }

        originalCertificate.setName(certificateDto.getName());
        originalCertificate.setDescription(certificateDto.getDescription());
        originalCertificate.setDuration(certificateDto.getDuration());
        originalCertificate.setPrice(certificateDto.getPrice());

        if (CollectionUtils.isNotEmpty(certificateDto.getTags())) {
            List<Tag> tagsToUpdate = certificateDto.getTags().stream()
                    .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                    .collect(Collectors.toList());

            tagsToUpdate = tagService.processTagList(tagsToUpdate);

            originalCertificate.setTags(tagsToUpdate);
        } else {
            originalCertificate.setTags(new ArrayList<>());
        }

        return modelMapper.map(certificateDao.update(originalCertificate), CertificateDto.class);
    }

    @Transactional
    @Override
    public CertificateDto update(CertificateUpdateDto dataToUpdate) {
        Certificate originalCertificate = certificateDao.findById(dataToUpdate.getId())
                .orElseThrow(() -> new EntryNotFoundException(ERROR_CERTIFICATE_NOT_FOUND,
                        dataToUpdate.getId().toString()));

        Set<String> tagsToAdd = extractTagNames(dataToUpdate.getTagsToAdd());
        Set<String> tagsToRemove = extractTagNames(dataToUpdate.getTagsToRemove());

        checkAddRemoveTagsIntersection(tagsToAdd, tagsToRemove);

        Set<String> tagsPresent = originalCertificate.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        checkAddPresentTagsIntersection(tagsToAdd, tagsPresent);

        checkRemovePresentTags(tagsToRemove, tagsPresent);

        removeTags(originalCertificate, tagsToRemove);
        addTags(originalCertificate, tagsToAdd);
        setNewFields(originalCertificate, dataToUpdate);

        return modelMapper.map(certificateDao.update(originalCertificate), CertificateDto.class);
    }

    // existence tags toRemove check before accessing db
    private void checkRemovePresentTags(Set<String> tagsToRemove, Set<String> tagsPresent) {
        if (CollectionUtils.isNotEmpty(tagsToRemove) && !tagsPresent.containsAll(tagsToRemove)) {
            tagsToRemove.removeAll(tagsPresent);
            throw new SubEntryNotFoundException(ERROR_TAG_NOT_FOUND, tagsToRemove.toString());
        }
    }

    // intersections check within toAdd and alreadyPresent tag's names before accessing db
    private void checkAddPresentTagsIntersection(Set<String> tagsToAdd, Set<String> tagsPresent) {
        Set<String> intersected = getIntersections(tagsToAdd, tagsPresent);
        if (!intersected.isEmpty()) {
            throw new SubEntryAlreadyAttachedException(intersected.toString());
        }
    }

    // intersections check within toAdd and toRemove tag's names before accessing db
    private void checkAddRemoveTagsIntersection(Set<String> tagsToAdd, Set<String> tagsToRemove) {
        Set<String> intersected = getIntersections(tagsToAdd, tagsToRemove);
        if (!intersected.isEmpty()) {
            throw new EntityIntersectionException(intersected);
        }
    }

    private Set<String> getIntersections(Set<String> setOne, Set<String> setTwo) {
        if (CollectionUtils.isNotEmpty(setOne) && CollectionUtils.isNotEmpty(setTwo)) {
            return new HashSet<>(CollectionUtils.intersection(setOne, setTwo));
        } else {
            return Collections.emptySet();
        }
    }

    // removing tags if toRemove collection is present
    private void removeTags(Certificate originalCertificate, Set<String> tagsToRemove) {
        if (CollectionUtils.isNotEmpty(tagsToRemove)) {
            detachTags(originalCertificate, tagsToRemove);
        }
    }

    // add tags if tagsToAdd collection is present and contains any items
    private void addTags(Certificate originalCertificate, Set<String> tagsToAdd) {
        if (CollectionUtils.isNotEmpty(tagsToAdd)) {
            attachTags(originalCertificate, tagsToAdd);
        }
    }

    // Set new values of present fields
    private void setNewFields(Certificate originalCertificate, CertificateUpdateDto dataToUpdate) {
        if (dataToUpdate.getName() != null) {
            originalCertificate.setName(dataToUpdate.getName());
        }

        if (dataToUpdate.getDescription() != null) {
            originalCertificate.setDescription(dataToUpdate.getDescription());
        }

        if (dataToUpdate.getDuration() != null) {
            originalCertificate.setDuration(dataToUpdate.getDuration());
        }

        if (dataToUpdate.getPrice() != null) {
            originalCertificate.setPrice(dataToUpdate.getPrice());
        }
    }

    // detach Tags to be removed from certificate
    private void detachTags(Certificate originalCertificate, Set<String> tagsToRemove) {
        originalCertificate.getTags().removeAll(tagsToRemove.stream()
                .map(tagName -> modelMapper.map(tagService.findByName(tagName), Tag.class))
                .collect(Collectors.toList()));
    }

    // attach Tags to be added to certificate
    private void attachTags(Certificate originalCertificate, Set<String> tagsToAdd) {
        List<Tag> tagsToProcess = tagsToAdd.stream()
                .map(name -> Tag.builder()
                        .name(name)
                        .build())
                .collect(Collectors.toList());

        List<Tag> tagsProcessed = tagService.processTagList(tagsToProcess);

        originalCertificate.getTags().addAll(tagsProcessed);
    }

    // Put names of TagDto into Set<String>
    private Set<String> extractTagNames(List<TagDto> tagsDto) {
        return Optional.ofNullable(tagsDto)
                .map(tags -> tags.stream()
                        .map(TagDto::getName)
                        .collect(Collectors.toSet())
                ).orElse(Collections.emptySet());
    }

    @Transactional
    @Override
    public CertificateDto deleteById(Long id) {
        Certificate certificate = certificateDao.findById(id)
                .orElseThrow(() -> new EntryNotFoundException(ERROR_CERTIFICATE_NOT_FOUND, id.toString()));
        certificateDao.delete(certificate);

        return modelMapper.map(certificate, CertificateDto.class);
    }
}
