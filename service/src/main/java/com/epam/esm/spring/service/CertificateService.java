package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateUpdateDto;

import java.util.List;
import java.util.Map;

public interface CertificateService extends CrudService<CertificateDto> {

    /**
     * Returns the list of certificates accordingly to search parameters.
     *
     * @param params list of search params with values
     * @return list of Certificates accordingly to search conditions
     */
    List<CertificateDto> findBy(Map<String, String> params);

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
}
