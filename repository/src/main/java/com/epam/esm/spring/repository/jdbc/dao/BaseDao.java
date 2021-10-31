package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class BaseDao<T extends AbstractEntity> implements ReadDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.empty();
    }
}
