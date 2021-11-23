package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.UserDto;

public interface UserService extends CrudService<UserDto> {

    /**
     * Returns User by username given
     *
     * @param username user name
     * @return User
     */
    UserDto findByUsername(String username);
}
