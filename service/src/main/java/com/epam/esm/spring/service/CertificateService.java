package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateParamDto;
import com.epam.esm.spring.service.dto.CertificateUpdateDto;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.Pageable;

import java.util.List;

public interface CertificateService extends CrudService<CertificateDto> {

    /**
     * Returns the list of certificates accordingly to search parameters.
     *
     * @param params list of search params with values
     * @return list of Certificates accordingly to search conditions
     */
    Page<CertificateDto> findBy(CertificateParamDto params, Pageable pageRequest);

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
