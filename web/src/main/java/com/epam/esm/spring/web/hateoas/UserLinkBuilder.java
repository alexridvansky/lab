package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.UserDto;
import com.epam.esm.spring.web.controller.UserController;
import org.springframework.stereotype.Component;

@Component
public class UserLinkBuilder extends AbstractLinkBuilder<UserDto> {

    @Override
    public UserDto addRemoveLink(UserDto userDto) {
        return addRemoveLinks(userDto, UserController.class);
    }

    @Override
    public UserDto addFindAllLink(UserDto userDto) {
        return addFindAllLink(userDto, UserController.class);
    }

    @Override
    public UserDto addFindByIdLink(UserDto userDto) {
        return addFindByIdLink(userDto, UserController.class);
    }
}
