package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.UserResponseDto;
import com.epam.esm.spring.web.controller.UserController;
import org.springframework.stereotype.Component;

@Component
public class UserLinkBuilder extends AbstractLinkBuilder<UserResponseDto> {

    @Override
    public UserResponseDto addRemoveLink(UserResponseDto userDto) {
        return addRemoveLinks(userDto, UserController.class);
    }

    @Override
    public UserResponseDto addFindAllLink(UserResponseDto userDto) {
        return addFindAllLink(userDto, UserController.class);
    }

    @Override
    public UserResponseDto addFindByIdLink(UserResponseDto userDto) {
        return addFindByIdLink(userDto, UserController.class);
    }
}
