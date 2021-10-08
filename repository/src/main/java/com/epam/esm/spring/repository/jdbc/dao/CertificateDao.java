package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CertificateDao extends Dao<Certificate> {
    /**
     * Is used to get the list of certificates
     *
     * @return List<Certificate>
     */
    List<Certificate> findAll();

    /**
     * Is used to get Certificate by id given
     *
     * @param id is id of the Certificate
     * @return Certificate
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
     * Updates only fields that not null
     *
     * @param id id of Certificate to be updated
     * @param data Map<String, Object> of data for fields to be updated
     * @return true of there were no errors during updating process
     */
    boolean update(Long id, Map<String, Object> data);

    /**
     * Is used to check it out whether Certificate with ID given exists
     *
     * @param id the name of Certificate to be looked for
     * @return true in case such Certificate found in the DB
     */
    boolean exists(long id);

    /**
     * Is used to check it out whether Certificate with NAME given exists
     *
     * @param name the name of Certificate to be looked for
     * @return true in case such Certificate found in the DB
     */
    boolean exists(String name);

    /**
     * Is used for storing tag list, related to the specific certificate
     *
     * @param tags List of Tags, to be stored
     * @param id Certificate id to attach tags to
     * @return true if process causes no errors
     */
    boolean insertTagIntoXrefTable(List<Tag> tags, long id);

    /**
     * Is used for removing all tags attached to the specific certificate
     *
     * @param id Certificate id to remove connected tags
     * @return true if process causes no errors
     */
    boolean detachTagFromXrefTable(long id);

    /**
     * Is used to delete Certificate by its ID
     *
     * @param id - Certificate's ID
     * @return true if Certificate has been deleted and false if wasn't
     */
    boolean deleteById(long id);
}
