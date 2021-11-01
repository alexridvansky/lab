package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.AbstractDto;
import com.epam.esm.spring.web.config.ConfigProperties;
import com.epam.esm.spring.web.controller.Controller;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class AbstractLinkBuilder<T extends AbstractDto> implements LinkBuilder<T> {

    private final ConfigProperties properties;
    private final static int DEFAULT_PAGE = 1;
    private final static int DEFAULT_PAGE_SIZE = 10;
    protected static final String LINK_TITLE_DELETE = "delete";
    protected static final String LINK_TITLE_FIND_ALL = "findAll";
    protected static final String LINK_TITLE_FIND_BY_ID = "findById";

    public AbstractLinkBuilder(ConfigProperties properties) {
        this.properties = properties;
    }

    protected <K extends Controller<T>> T addRemoveLinks(T entity, Class<K> controllerClass) {
        Link removeLink = linkTo(methodOn(controllerClass).remove(entity.getId())).withRel(LINK_TITLE_DELETE);
        entity.add(removeLink);

        return entity;
    }

    protected <K extends Controller<T>> T addAllFindAllLink(T entity, Class<K> controllerClass) {
        Link findAllLink = linkTo(methodOn(controllerClass)
                .findAll(properties.getOffsetDefault(), properties.getLimitDefault())).withRel(LINK_TITLE_FIND_ALL);
        entity.add(findAllLink);

        return entity;
    }

    protected <K extends Controller<T>> T addAllFindByIdLink(T entity, Class<K> controllerClass) {
        Link findByIdLink = linkTo(methodOn(controllerClass).findById(entity.getId())).withRel(LINK_TITLE_FIND_BY_ID);
        entity.add(findByIdLink);

        return entity;
    }
}
