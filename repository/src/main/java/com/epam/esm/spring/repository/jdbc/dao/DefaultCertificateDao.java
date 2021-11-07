package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.jdbc.querybuilder.QueryBuilder;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.CertificateParam;
import com.epam.esm.spring.repository.model.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.NAME;

@Repository
public class DefaultCertificateDao implements CertificateDao {

    private static final int EMPTY_RESULT = 0;
    private final QueryBuilder queryBuilder;

    public DefaultCertificateDao(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Certificate> findAll(Pageable pageParam) {
        return entityManager.createQuery("SELECT c FROM Certificate c", Certificate.class)
                .setFirstResult(pageParam.getPage() * pageParam.getSize())
                .setMaxResults(pageParam.getSize())
                .getResultList();
    }

    @Override
    public Long countEntry() {
        return entityManager.createQuery("SELECT COUNT(c) FROM Certificate c", Long.class)
                .getSingleResult();
    }

    @Override
    public List<Certificate> findBy(CertificateParam param, Pageable pageParam) {
        TypedQuery<Certificate> typedQuery = entityManager.createQuery(queryBuilder.buildQueryForSearch(param));
        return typedQuery
                .setFirstResult(pageParam.getPage() * pageParam.getSize())
                .setMaxResults(pageParam.getSize())
                .getResultList();
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
        return entityManager.createQuery("SELECT COUNT(c) FROM Certificate c WHERE c.name = :name", Long.class)
                .setParameter(NAME, name)
                .getSingleResult() > EMPTY_RESULT;
    }

    @Override
    public void delete(Certificate certificate) {
        entityManager.remove(certificate);
    }
}
