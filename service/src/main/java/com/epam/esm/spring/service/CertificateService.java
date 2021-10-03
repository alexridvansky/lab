package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;

import java.util.List;

public interface CertificateService {
    /**
     * Is used to get the list of all certificates
     *
     * @return List<CertificateDto>
     */
    List<CertificateDto> findAll();

    /**
     * Is used to get Certificate by id given
     *
     * @param id is id of the Certificate
     * @return TagDto.class instance if Certificate with id given is present in the db
     */
    CertificateDto findById(long id) throws EntryNotFoundException;

    /**
     * Adds new Certificate entry to the db
     *
     * @param certificateDto Certificate to be added to
     * @return CertificateDto.class object just added to the db
     */
    CertificateDto insert(CertificateDto certificateDto);
}
