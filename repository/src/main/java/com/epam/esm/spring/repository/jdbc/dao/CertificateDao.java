package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDao {

    List<Certificate> findAll();

    Optional<Certificate> findById(long id);

    Certificate insert(Certificate certificate);
}
