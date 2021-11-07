package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.AbstractDto;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;

public interface CrudService<T extends AbstractDto> {
    /**
     * Gets the list of all entities
     *
     * @return List<T>
     */
    Page<T> findAll(PageableDto pageRequest);

    /**
     * Gets Entity by id given
     *
     * @param id is id of the Entity
     * @return T object instance if such with ID given found
     */
    T findById(Long id);

    /**
     * Adds new entry to the db
     *
     * @param t entity object to be added to
     * @return T.class object just added to the db
     */
    T insert(T t);

    /**
     * Removes entity by its ID
     *
     * @param id - entity's ID
     * @return Entity which has been deleted
     */
    T deleteById(Long id);
}
