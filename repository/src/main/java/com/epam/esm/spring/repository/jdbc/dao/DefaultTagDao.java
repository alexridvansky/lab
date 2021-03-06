package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Pageable;
import com.epam.esm.spring.repository.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.SQL_FIND_MOST_USED_TAG;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.ID;
import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.NAME;

@Repository
public class DefaultTagDao implements TagDao {

    public static final int EMPTY_RESULT = 0;
    public static final int MAX_RESULT_ONE = 1;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Tag insert(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public List<Tag> findAll(Pageable pageParam) {
        return entityManager.createQuery("SELECT t FROM Tag t ORDER BY t.id", Tag.class)
                .setFirstResult(pageParam.getPage() * pageParam.getSize())
                .setMaxResults(pageParam.getSize())
                .getResultList();
    }

    @Override
    public Long countEntry() {
        return entityManager.createQuery("SELECT COUNT(t) FROM Tag t", Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class)
                .setParameter(NAME, name)
                .setMaxResults(MAX_RESULT_ONE)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public boolean isExist(String name) {
        return entityManager.createQuery("SELECT COUNT(t) FROM Tag t WHERE t.name = :name", Long.class)
                .setParameter(NAME, name)
                .getSingleResult() > EMPTY_RESULT;
    }

    @Override
    public boolean isExist(Long id) {
        return (long) entityManager.createQuery("SELECT COUNT(t) FROM Tag t WHERE t.id = :id")
                .setParameter(ID, id)
                .getSingleResult() > EMPTY_RESULT;
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> findMostUsed() {
        return entityManager.createNativeQuery(SQL_FIND_MOST_USED_TAG, Tag.class)
                .getResultList()
                .stream()
                .findAny();
    }
}