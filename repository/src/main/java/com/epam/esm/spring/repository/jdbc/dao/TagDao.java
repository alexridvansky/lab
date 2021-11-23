package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Tag;

import java.util.Optional;

public interface TagDao extends CreateReadDeleteDao<Tag> {

    /**
     * Is used to get Tag by name given
     *
     * @param name is the name of the Tag
     * @return Tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Is used to check it out whether Tag with NAME given exists
     *
     * @param name the name of Tag
     * @return true in case such Tag found in the DB
     */
    boolean isExist(String name);

    /**
     * Is used to check it out whether Tag with ID given exists
     *
     * @param id the Tag ID
     * @return true in case such Tag found in the DB
     */
    boolean isExist(Long id);

    /**
     * Gets the most widely used tag of a user with the highest cost of all orders.
     *
     * @return Tag tag
     */
    Optional<Tag> findMostUsed();
}