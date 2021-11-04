package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.CertificateService;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateParamDto;
import com.epam.esm.spring.service.dto.CertificateUpdateDto;
import com.epam.esm.spring.web.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

/**
 * Controller provides service within GiftCertificate.class entities.
 */
@RestController
@Validated
@RequestMapping("/api/certificates")
public class CertificateController implements Controller<CertificateDto> {

    private final CertificateService certificateService;
    private final ConfigProperties properties;

    @Autowired
    public CertificateController(CertificateService certificateService,
                                 ConfigProperties properties) {
        this.certificateService = certificateService;
        this.properties = properties;
    }

    @Override
    @GetMapping("/{id}")
    public CertificateDto findById(@PathVariable Long id) {
        return certificateService.findById(id);
    }

    @Override
    @RequestMapping(params = {"page", "size"})
    public List<CertificateDto> findAll(@RequestParam(name = "page", required = false) Integer page,
                                        @RequestParam(name = "size", required = false) Integer size) {
        if (page == null) {
            page = properties.getOffsetDefault();
        }
        if (size == null) {
            size = properties.getLimitDefault();
        }

        return certificateService.findAll();
    }

    /**
     * Is used for getting list of Certificates
     *
     * @param tags   tag names
     * @param search part of Certificate's name or description
     * @param sort   field to sort by
     * @param order  ASC or DESC
     * @return List<CertificateDto> the list of certificates
     */
    @GetMapping
    public List<CertificateDto> findAll(
            @RequestParam(required = false, name = "tags", defaultValue = "") Set<String> tags,
            @RequestParam(required = false, name = "search", defaultValue = "") String search,
            @RequestParam(required = false, name = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, name = "order", defaultValue = "") String order) {

        if (tags.isEmpty() && search.isEmpty() && sort.isEmpty() && order.isEmpty()) {
            return findAll(properties.getOffsetDefault(), properties.getLimitDefault());
        } else {
            CertificateParamDto paramDto = CertificateParamDto.builder()
                    .tags(tags)
                    .search(search)
                    .sort(sort)
                    .order(order)
                    .build();

            return certificateService.findBy(paramDto);
        }
    }

    /**
     * Inserts new Certificate
     *
     * @return CertificateDto just inserted
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto insert(@RequestBody CertificateDto certificateDto) {
        return certificateService.insert(certificateDto);
    }

    /**
     * Updates Certificate partially
     *
     * @return CertificateDto just updated
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto update(@Validated @RequestBody CertificateUpdateDto certificateUpdateDto,
                                 @PathVariable @Positive Long id) {
        certificateUpdateDto.setId(id);
        return certificateService.update(certificateUpdateDto);
    }

    /**
     * Updates Certificate entirely
     *
     * @return CertificateDto just updated
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto update(@Validated @RequestBody CertificateDto certificateDto,
                                 @PathVariable @Positive Long id) {
        certificateDto.setId(id);
        return certificateService.update(certificateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        certificateService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
