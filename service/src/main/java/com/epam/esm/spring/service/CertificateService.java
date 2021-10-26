package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateUpdateDto;

import java.util.List;
import java.util.Map;

public interface CertificateService {

    /**
     * Returns the list of all certificates.
     *
     * @return List<CertificateDto>
     */
    List<CertificateDto> findAll();

    /**
     * Returns the list of certificates accordingly to search parameters.
     *
     * @param params list of search params with values
     * @return list of Certificates accordingly to search conditions
     */
    List<CertificateDto> findBy(Map<String, String> params);

    /**
     * Returns Certificate by id given.
     *
     * @param id is id of the Certificate
     * @return TagDto.class instance if Certificate with id given is present in the db
     */
    CertificateDto findById(long id);

    /**
     * Adds new Certificate entry to the db
     *
     * @param certificateDto Certificate to be added to
     * @return CertificateDto.class object just added to the db
     */
    CertificateDto insert(CertificateDto certificateDto);

    /**
     * Updates existing Certificate entirely
     *
     * @param certificateDto new entity to replace existent
     * @return CertificateDto updated object
     */
    CertificateDto update(CertificateDto certificateDto);

    /**
     * Updates existing Certificate only by fields provided
     *
     * @param certificateUpdateDto with only fields to be updated
     * @return CertificateDto updated object
     */
    CertificateDto update(CertificateUpdateDto certificateUpdateDto);

    /**
     * Deletes Certificate by its ID
     *
     * @param id - Certificate ID
     * @return CertificateDto which was deleted
     */
    CertificateDto deleteById(long id);
}
