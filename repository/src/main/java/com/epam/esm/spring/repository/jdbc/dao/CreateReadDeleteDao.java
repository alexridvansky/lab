package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.AbstractEntity;

public interface CreateReadDeleteDao<T extends AbstractEntity> extends ReadDao<T> {

    /**
     * Adds new entry of AbstractEntity to the db
     *
     * @param t AbstractEntity object to be added to
     * @return AbstractEntity object just added to the db
     */
    T insert(T t);

    /**
     * Deletes AbstractEntity by its ID.
     *
     * @param t - AbstractEntity
     */
    void delete(T t);
}