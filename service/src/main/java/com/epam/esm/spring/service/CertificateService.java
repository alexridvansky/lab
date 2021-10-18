package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.CertificateDto;

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
     * @param params
     * @return
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
     * Updates existing Certificate
     *
     * @param certificateDto with only fields to be updated
     * @return CertificateDto updated object
     */
    CertificateDto update(CertificateDto certificateDto);

    /**
     * Checks whether every Tag stored to the list
     * is present in the db, if so - read and store id for it,
     * if not - creates and stores id of just created one
     *
     * @param certificateDto CertificateDto entity
     */
    void processTagList(CertificateDto certificateDto);

    /**
     * Deletes Certificate by its ID
     *
     * @param id - Certificate ID
     * @return CertificateDto which was deleted
     */
    CertificateDto deleteById(long id);
}
