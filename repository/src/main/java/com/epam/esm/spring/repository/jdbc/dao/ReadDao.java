package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.AbstractEntity;
import com.epam.esm.spring.repository.model.PageParam;

import java.util.List;
import java.util.Optional;

public interface ReadDao<T extends AbstractEntity> {
    /**
     * Returns the list of AbstractEntity.
     *
     * @return List<AbstractEntity>
     */
    List<T> findAll(PageParam pageParam);

    /**
     * returns number of entries in the db.
     *
     * @return Long number of records.
     */
    Long countEntry();

    /**
     * Returns AbstractEntity by id given.
     *
     * @param id id of the AbstractEntity
     * @return AbstractEntity if found or Optional.empty if not
     */
    Optional<T> findById(Long id);
}
