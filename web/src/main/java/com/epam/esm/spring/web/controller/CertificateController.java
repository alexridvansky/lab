package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.CertificateService;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateParamDto;
import com.epam.esm.spring.service.dto.CertificateUpdateDto;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.Pageable;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Controller provides service within GiftCertificate.class entities.
 */
@RestController
@Validated
@RequestMapping("/api/certificates")
public class CertificateController implements Controller<CertificateDto> {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @Override
    @GetMapping("/{id}")
    public CertificateDto findById(@PathVariable Long id) {
        return certificateService.findById(id);
    }

    @Override
    public ResponseEntity<Page<CertificateDto>> findAll(@Valid Pageable pageRequest) {
        return new ResponseEntity<>(certificateService.findAll(pageRequest), HttpStatus.OK);
    }

    /**
     * Is used for getting list of Certificates
     *
     * @param searchParamDto Search parameters
     * @return List<CertificateDto> the list of certificates
     */
    @GetMapping
    public Page<CertificateDto> findBy(@Valid Pageable pageable, CertificateParamDto searchParamDto) {
        if (searchParamDto.isRequestMeaningless()) {
            return certificateService.findAll(pageable);
        } else {
            return certificateService.findBy(searchParamDto, pageable);
        }
    }

    /**
     * Inserts new Certificate
     *
     * @return CertificateDto just inserted
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto insert(@Valid @RequestBody CertificateDto certificateDto) {
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
