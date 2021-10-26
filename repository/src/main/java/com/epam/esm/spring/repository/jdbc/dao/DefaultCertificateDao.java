package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DefaultCertificateDao implements CertificateDao {

    public static final int EMPTY_RESULT = 0;
    private final CriteriaBuilder criteriaBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public DefaultCertificateDao(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    @Override
    public List<Certificate> findAll() {
        return entityManager.createQuery("SELECT c FROM Certificate c", Certificate.class).getResultList();
    }

    @Override
    public List<Certificate> findBy(Map<String, String> params) {

        // parametrized search is under construction

        return null; //todo: not to forget to replace return value !!!!
    }

    @Override
    public Optional<Certificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Override
    public Certificate insert(Certificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public Certificate update(Certificate certificate) {
        return entityManager.merge(certificate);
    }

    @Override
    public boolean isExist(String name) {
        return (long) entityManager.createQuery("SELECT COUNT(c) FROM Certificate c WHERE c.name = :name")
                .setParameter("name", name)
                .getSingleResult() > EMPTY_RESULT;
    }

    @Override
    public void delete(Certificate certificate) {
        entityManager.remove(certificate);
    }
}
