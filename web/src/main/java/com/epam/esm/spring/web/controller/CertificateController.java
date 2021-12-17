package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.CertificateService;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateParamDto;
import com.epam.esm.spring.service.dto.CertificateUpdateDto;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.web.hateoas.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * Controller provides service within GiftCertificate.class entities.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/certificates")
public class CertificateController implements Controller<CertificateDto> {

    private final CertificateService certificateService;
    private final LinkBuilder<CertificateDto> linkBuilder;

    @Override
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public CertificateDto findById(@PathVariable Long id) {
        CertificateDto certificateDto = certificateService.findById(id);
        linkBuilder.addRemoveLink(certificateDto);
        linkBuilder.addFindAllLink(certificateDto);

        return certificateDto;
    }

    @Override
    @PreAuthorize("permitAll()")
    public Page<CertificateDto> findAll(@Valid PageableDto pageRequest) {
        return certificateService.findAll(pageRequest);
    }

    /**
     * Is used for getting list of Certificates
     *
     * @param searchParamDto Search parameters
     * @return List<CertificateDto> the list of certificates
     */
    @PreAuthorize("permitAll()")
    @GetMapping
    public Page<CertificateDto> findBy(@Valid PageableDto pageableDto, CertificateParamDto searchParamDto) {
        if (searchParamDto.isRequestMeaningless()) {
            return certificateService.findAll(pageableDto);
        } else {
            return certificateService.findBy(searchParamDto, pageableDto);
        }
    }

    /**
     * Inserts new Certificate
     *
     * @return CertificateDto just inserted
     */
    @PreAuthorize("hasRole('ROLE_ROOT')")
    @PostMapping()
    public ResponseEntity<Void> insert(@Valid @RequestBody CertificateDto certificateDto) {
        certificateService.insert(certificateDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Updates Certificate partially
     *
     * @return CertificateDto just updated
     */
    @PreAuthorize("hasRole('ROLE_ROOT')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CertificateUpdateDto certificateUpdateDto,
                                       @PathVariable @Positive Long id) {
        certificateUpdateDto.setId(id);
        certificateService.update(certificateUpdateDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates Certificate entirely
     *
     * @return CertificateDto just updated
     */
    @PreAuthorize("hasRole('ROLE_ROOT')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CertificateDto certificateDto,
                                       @PathVariable @Positive Long id) {
        certificateDto.setId(id);
        certificateService.update(certificateDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        certificateService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
