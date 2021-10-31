package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.UserDao;
import com.epam.esm.spring.repository.model.User;
import com.epam.esm.spring.service.dto.UserDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_USER_NOT_FOUND;

@Service
public class DefaultUserService implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;

    @Autowired
    DefaultUserService(UserDao userDao,
                       ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDto> findAll() {
        return userDao.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        return userDao.findById(id).map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntryNotFoundException(ERROR_USER_NOT_FOUND, id.toString()));
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new EntryNotFoundException(ERROR_USER_NOT_FOUND, username));
    }

    @Override
    public UserDto insert(UserDto userDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDto deleteById(Long id) {
        throw new UnsupportedOperationException();
    }
}
