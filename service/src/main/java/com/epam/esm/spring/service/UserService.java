package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.UserResponseDto;

public interface UserService extends CrudService<UserResponseDto> {

    /**
     * Returns User by username given
     *
     * @param username user name
     * @return User
     */
    UserResponseDto findByUsername(String username);
}
