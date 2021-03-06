package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.UserDao;
import com.epam.esm.spring.repository.jdbc.jparepository.UserRepository;
import com.epam.esm.spring.repository.model.Pageable;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.UserResponseDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.util.PageRequestProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_USER_NOT_FOUND;

@Service
public class DefaultUserService implements UserService {

    private final UserDao userDao;
    private final PageRequestProcessor pageRequestProcessor;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    DefaultUserService(UserDao userDao,
                       PageRequestProcessor pageRequestProcessor,
                       ModelMapper modelMapper,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.pageRequestProcessor = pageRequestProcessor;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserResponseDto> findAll(PageableDto pageRequest) {
        pageRequestProcessor.processRequest(pageRequest);

        List<UserResponseDto> result =  userDao.findAll(modelMapper.map(pageRequest, Pageable.class))
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());

        Long totalPages = userDao.countEntry();

        return new Page<>(result, pageRequest, totalPages);
    }

    @Override
    public UserResponseDto findById(Long id) {
        return userDao.findById(id).map(user -> modelMapper.map(user, UserResponseDto.class))
                .orElseThrow(() -> new EntryNotFoundException(ERROR_USER_NOT_FOUND, id.toString()));
    }

    @Override
    public UserResponseDto findByUsername(String username) {
        return userDao.findByUsername(username).map(user -> modelMapper.map(user, UserResponseDto.class))
                .orElseThrow(() -> new EntryNotFoundException(ERROR_USER_NOT_FOUND, username));
    }

    @Override
    public UserResponseDto insert(UserResponseDto userDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserResponseDto deleteById(Long id) {
        throw new UnsupportedOperationException();
    }
}