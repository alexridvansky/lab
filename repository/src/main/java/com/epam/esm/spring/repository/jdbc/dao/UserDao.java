package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.User;

import java.util.Optional;

public interface UserDao extends ReadDao<User> {

    /**
     * Finds user by name
     *
     * @param username User's name
     * @return Optional<User> if found and Optional.Empty if not
     */
    Optional<User> findByUsername(String username);
}
