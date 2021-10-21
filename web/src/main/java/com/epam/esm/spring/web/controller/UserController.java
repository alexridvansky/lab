package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.repository.model.User;
import com.epam.esm.spring.service.UserService;
import com.epam.esm.spring.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Controller provides service within User.class entities.
 */
@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Is used for getting list of Users.
     *
     * @return List<UserDto> the list of users
     */
    @GetMapping()
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    /**
     * Is used for getting User by ID.
     *
     * @return UserDto
     */
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable @Positive Long id) {
        return userService.findById(id);
    }
}
