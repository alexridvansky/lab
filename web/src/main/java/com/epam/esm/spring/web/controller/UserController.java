package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.UserService;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.UserDto;
import com.epam.esm.spring.web.hateoas.LinkBuilder;
import com.epam.esm.spring.web.hateoas.UserLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@RestController
@Validated
@RequestMapping(value = "/api/users")
public class UserController implements Controller<UserDto> {

    private final UserService userService;
    private final LinkBuilder<UserDto> linkBuilder;

    @Autowired
    public UserController(UserService userService,
                          UserLinkBuilder linkBuilder) {
        this.userService = userService;
        this.linkBuilder = linkBuilder;
    }

    @Override
    @GetMapping()
    public Page<UserDto> findAll(@Valid PageableDto pageRequest) {
        return userService.findAll(pageRequest);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable @Positive Long id) {
        UserDto user = userService.findById(id);
        linkBuilder.addFindAllLink(user);
        linkBuilder.addRemoveLink(user);

        return user;
    }

    @Override
    public ResponseEntity<Void> remove(Long id) {
        userService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
