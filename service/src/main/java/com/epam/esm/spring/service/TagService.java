package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.TagDto;

import java.util.List;

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
     * @return TagDto object instance if Tag with ID given found
     */
    TagDto findById(long id);

    /**
     * Is used to get Tag by NAME given
     *
     * @param name is the NAME of the Tag
     * @return TagDto object instance if Tag with NAME given found
     */
    TagDto findByName(String name);

    /**
     * Adds new entry to the db
     *
     * @param tag Tag.class object to be added to
     * @return Tag.class object just added to the db
     */
    TagDto insert(TagDto tag);

    /**
     * Is used to delete tag by its ID
     *
     * @param id - tag ID
     * @return TagDto which was deleted
     */
    TagDto deleteById(long id);

    /**
     * Is used to check it out whether Tag with NAME given exists
     *
     * @param name the name of Tag
     * @return true in case such Tag found in the DB
     */
    boolean isExist(String name);
}
