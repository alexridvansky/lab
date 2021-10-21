package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    /**
     * Finds users in the db.
     *
     * @return list of users, presented into db
     */
    List<User> findAll();

    /**
     * Finds user by name
     *
     * @param username User's name
     * @return Optional<User> if found and Optional.Empty if not
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds user by id
     *
     * @param id User's id
     * @return Optional<User> if found and Optional.Empty if not
     */
    Optional<User> findById(Long id);
}
