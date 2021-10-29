package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.AbstractDto;

public interface LinkBuilder<T extends AbstractDto> {

    /**
     * adds link to entity to remove method of endpoint
     *
     * @param t the entity link have to be added to
     * @return the same entity
     */
    T addRemoveLink(T t);
}