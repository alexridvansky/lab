package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.TagDto;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

public interface TagService {
    /**
     * Is used to get the list of all certificates' tags
     *
     * @return List<Tag>
     */
    List<TagDto> findAll();

    /**
     * Is used to get Tag by id given
     *
     * @param id is id of the Tag
     * @return Optional.TagDto class instance if Tag with id given is present in the db
     */
    TagDto findById(long id);

    /**
     * Adds new entry to the db
     *
     * @param tag Tag.class object to be added to
     * @return Tag.class object just added to the db
     */
    Tag insert(Tag tag);

}
