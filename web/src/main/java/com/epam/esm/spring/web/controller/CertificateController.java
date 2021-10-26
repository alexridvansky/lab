package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.CertificateService;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller provides service within GiftCertificate.class entities.
 */
@RestController
@Validated
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Is used for getting Certificate by ID
     *
     * @return CertificateDto
     */
    @GetMapping("/{id}")
    public CertificateDto findById(@PathVariable @Positive Long id) {
        return certificateService.findById(id);
    }

    /**
     * Is used for getting list of Certificates
     *
     * @param tag    tag name
     * @param search part of Certificate's name or description
     * @param sort   field to sort by
     * @param order  ASC or DESC
     * @return List<CertificateDto> the list of certificates
     */
    @GetMapping()
    public List<CertificateDto> findAll(
            @RequestParam(required = false, name = "tag", defaultValue = "") String tag,
            @RequestParam(required = false, name = "search", defaultValue = "") String search,
            @RequestParam(required = false, name = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, name = "order", defaultValue = "") String order) {
        if (tag.isEmpty() && search.isEmpty() && sort.isEmpty() && order.isEmpty()) {
            return certificateService.findAll();
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("tag", tag);
            params.put("search", search);
            params.put("sort", sort);
            params.put("order", order);
            return certificateService.findBy(params);
        }
    }

    /**
     * Is used for inserting new Certificate
     *
     * @return CertificateDto just inserted
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
        public CertificateDto insert(@Validated(CertificateDto.Create.class)
                                        @RequestBody CertificateDto certificateDto) {
        return certificateService.insert(certificateDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto update(@Validated @RequestBody CertificateUpdateDto certificateUpdateDto,
                                 @PathVariable @Positive Long id) {
        certificateUpdateDto.setId(id);
        return certificateService.update(certificateUpdateDto);
    }

    /**
     * Is used for Removing Certificate by ID given
     *
     * @param id the id of Certificate to remove
     */
    @DeleteMapping("/{id}")
    public CertificateDto deleteCertificate(@PathVariable @Positive Long id) {
        return certificateService.deleteById(id);
    }
}
