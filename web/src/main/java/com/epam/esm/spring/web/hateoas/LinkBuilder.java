package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.AbstractDto;

public interface LinkBuilder<T extends AbstractDto> {

    /**
     * adds link to entity to remove method of endpoint
     *
     * @param t the entity link have to be added to
     * @return entity with added link
     */
    T addRemoveLink(T t);

    /**
     *  Adds link to findAll method of the endpoint
     * @param t the entity link have to be added to
     * @return entity with added link
     */
    T addFindAllLink(T t);

    /**
     *  Adds lint to findById method of the endpoint
     * @param t the entity link have to be added to
     * @return entity with added link
     */
    T addFindByIdLink(T t);
}