package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.jdbc.querybuilder.QueryBuilder;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.CertificateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.NAME;

@Repository
public class DefaultCertificateDao implements CertificateDao {

    public static final int EMPTY_RESULT = 0;
    public final QueryBuilder queryBuilder;

    public DefaultCertificateDao(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Certificate> findAll() {
        return entityManager.createQuery("SELECT c FROM Certificate c", Certificate.class).getResultList();
    }

    @Override
    public List<Certificate> findBy(CertificateParam param) {
        TypedQuery<Certificate> typedQuery = entityManager.createQuery(queryBuilder.buildQueryForSearch(param));
        return typedQuery.getResultList();
    }

    @Override
    public Optional<Certificate> findById(Long id) {
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
                .setParameter(NAME, name)
                .getSingleResult() > EMPTY_RESULT;
    }

    @Override
    public void delete(Certificate certificate) {
        entityManager.remove(certificate);
    }
}
