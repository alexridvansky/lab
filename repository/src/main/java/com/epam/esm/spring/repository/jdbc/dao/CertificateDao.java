package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Certificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CertificateDao extends Dao<Certificate> {
    /**
     * Returns the list of certificates.
     *
     * @return List<Certificate>
     */
    List<Certificate> findAll();

    /**
     * Returns the list of certificates accordingly to search parameters.
     *
     * @param params list of parameters for search
     * @return List<Certificate>
     */
    List<Certificate> findBy(Map<String, String> params);

    /**
     * Returns Certificate by id given.
     *
     * @param id is id of the Certificate
     * @return Certificate if found or Optional.empty if not
     */
    Optional<Certificate> findById(long id);

    /**
     * Adds new entry of Certificate to the db
     *
     * @param certificate Certificate.class object to be added to
     * @return Certificate.class object just added to the db
     */
    Certificate insert(Certificate certificate);

    /**
     * Updates certificate entry in the db
     *
     * @param certificate is a Certificate to be updated
     * @return Certificate just updated
     */
    Certificate update(Certificate certificate);

    /**
     * Checks whether Certificate with name given exists.
     *
     * @param name the name of Certificate to be looked for
     * @return true in case such Certificate found in the DB
     */
    boolean isExist(String name);

    /**
     * Deletes Certificate by its ID.
     *
     * @param certificate - Certificate to be deleted
     */
    void delete(Certificate certificate);
}
