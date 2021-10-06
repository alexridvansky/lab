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
     * Is used to get Tag by name given
     *
     * @param name is the name of the Tag
     * @return Tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Adds new entry to the db
     *
     * @param tag Tag.class object to be added to
     * @return Tag.class object just added to the db
     */
    Tag insert(Tag tag);

    /**
     * Is used to check it out whether Tag with NAME given exists
     *
     * @param name the name of Tag
     * @return true in case such Tag found in the DB
     */
    boolean isExists(String name);

    /**
     * Is used to check it out whether Tag with ID given exists
     *
     * @param id the Tag ID
     * @return true in case such Tag found in the DB
     */
    boolean isExists(long id);

    /**
     * Is used to check whether Tag with ID given is used in any Certificates
     *
     * @param id Tag ID
     * @return true if it has any relative certificates
     */
    boolean isUsed(long id);

    /**
     * Is used to delete tag by its ID
     *
     * @param id - tag ID
     * @return true if tag was deleted and false if wasn't
     */
    boolean deleteById(long id);
}
