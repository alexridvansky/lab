package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.CertificateService;
import com.epam.esm.spring.service.dto.CertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller provides service within GiftCertificate.class entities.
 */
@RestController
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
    public CertificateDto findById(@PathVariable Long id) {
        return certificateService.findById(id);
    }

    /**
     * Is used for getting list of Certificates
     *
     * @param tag
     * @param search
     * @param sort
     * @param order
     * @return List<CertificateDto> the list of certificates
     */
    @GetMapping()
    public List<CertificateDto> findAll(
            @RequestParam(required = false, name = "tag", defaultValue = "") String tag,
            @RequestParam(required = false, name = "search", defaultValue = "") String search,
            @RequestParam(required = false, name = "sort", defaultValue = "") String sort,
            @RequestParam(required = false, name = "order", defaultValue = "ASC") String order) {
        if (tag == null && search == null && sort == null && order == null) {
            return certificateService.findAll();
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("tag", tag);
            params.put("search", search);
            params.put("sort", sort);
            params.put("order", order);
            return certificateService.findAllByParam(params);
        }
    }

    /**
     * Is used for inserting new Certificate
     *
     * @return CertificateDto just inserted
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto insert(@RequestBody CertificateDto certificateDto) {
        return certificateService.insert(certificateDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto update(@RequestBody CertificateDto certificateDto) {
        return certificateService.update(certificateDto);
    }

    /**
     * Is used for Removing Certificate by ID given
     *
     * @param id the id of Certificate to remove
     */
    @DeleteMapping("/{id}")
    public CertificateDto deleteCertificate(@PathVariable Long id) {
        return certificateService.deleteById(id);
    }
}
