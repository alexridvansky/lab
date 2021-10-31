package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.web.controller.TagController;
import org.springframework.stereotype.Component;

@Component
public class TagLinkBuilder extends AbstractLinkBuilder<TagDto> {

    @Override
    public TagDto addRemoveLink(TagDto tagDto) {
        return addRemoveLinks(tagDto, TagController.class);
    }

    @Override
    public TagDto addFindAllLink(TagDto tagDto) {
        return addAllFindAllLink(tagDto, TagController.class);
    }

    @Override
    public TagDto addFindByIdLink(TagDto tagDto) {
        return addAllFindByIdLink(tagDto, TagController.class);
    }
}
