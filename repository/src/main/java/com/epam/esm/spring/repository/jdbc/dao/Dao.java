package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends AbstractEntity> {
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
    Optional<T> findById(long id);

    /**
     * Adds new entry of AbstractEntity to the db
     *
     * @param t AbstractEntity object to be added to
     * @return AbstractEntity object just added to the db
     */
    T insert(T t);

//    /**
//     * Deletes AbstractEntity by its ID.
//     *
//     * @param id - AbstractEntity's ID
//     * @return true if AbstractEntity has been deleted and false if wasn't
//     */
//    boolean deleteById(long id);
}
