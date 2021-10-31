package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface ReadDao<T extends AbstractEntity> {
    /**
     * Returns the list of AbstractEntity.
     *
     * @return List<AbstractEntity>
     */
    List<T> findAll();

    /**
     * Returns AbstractEntity by id given.
     *
     * @param id id of the AbstractEntity
     * @return AbstractEntity if found or Optional.empty if not
     */
    Optional<T> findById(Long id);
}
