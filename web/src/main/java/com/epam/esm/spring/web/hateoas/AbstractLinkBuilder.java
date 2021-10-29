package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.AbstractDto;
import com.epam.esm.spring.web.controller.Controller;
import com.epam.esm.spring.web.controller.TagController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class AbstractLinkBuilder<T extends AbstractDto> implements LinkBuilder<T> {

    protected static final String DELETE = "delete";

    protected <K extends Controller<AbstractDto>> T addRemoveLinks(T entity, Class<K> controllerClass) {
        Link removeLink = linkTo(methodOn(controllerClass).remove(entity.getId())).withRel(DELETE);
        entity.add(removeLink);

        return entity;
    }
}
