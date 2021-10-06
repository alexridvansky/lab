package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.CertificateService;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return List<CertificateDto> the list of certificates
     */
    @GetMapping()
    public List<CertificateDto> findAll() {
        return certificateService.findAll();
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
