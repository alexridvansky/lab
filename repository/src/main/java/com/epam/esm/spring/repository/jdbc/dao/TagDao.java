package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    /**
     * Is used to get the list of all certificates' tags
     *
     * @return List<Tag>
     */
    List<Tag> findAll();

    /**
     * Is used to get Tag by id given
     *
     * @param id is id of the Tag
     * @return Tag
     */
    Optional<Tag> findById(long id);

    /**
     * Adds new entry to the db
     *
     * @param tag Tag.class object to be added to
     * @return Tag.class object just added to the db
     */
    Tag insert(Tag tag);
}
