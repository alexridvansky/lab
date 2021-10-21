package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.model.User;
import com.epam.esm.spring.service.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Returns list of all users
     *
     * @return list of users
     */
    List<UserDto> findAll();

    /**
     * Returns User by user ID given
     *
     * @param id user id
     * @return User
     */
    UserDto findById(Long id);

    /**
     * Returns User by username given
     *
     * @param username user name
     * @return User
     */
    User findByUsername(String username);
}
