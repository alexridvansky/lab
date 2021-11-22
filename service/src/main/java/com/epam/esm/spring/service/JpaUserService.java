package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.jparepository.UserRepository;
import com.epam.esm.spring.repository.model.User;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.UserDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.util.PageRequestProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_USER_NOT_FOUND;

@Service
public class JpaUserService implements UserService {

    private static final String USER_NOT_FOUND_ERROR = "error.user_not_found";

    private final PageRequestProcessor pageRequestProcessor;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    JpaUserService(PageRequestProcessor pageRequestProcessor,
                   ModelMapper modelMapper,
                   UserRepository userRepository,
                   PasswordEncoder passwordEncoder) {
        this.pageRequestProcessor = pageRequestProcessor;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserDto> findAll(PageableDto pageRequest) {
//        pageRequestProcessor.processRequest(pageRequest);
//
//        List<UserDto> result =  userDao.findAll(modelMapper.map(pageRequest, Pageable.class))
//                .stream()
//                .map(user -> modelMapper.map(user, UserDto.class))
//                .collect(Collectors.toList());
//
//        Long totalPages = userDao.countEntry();
//
//        return new Page<>(result, pageRequest, totalPages);
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntryNotFoundException(ERROR_USER_NOT_FOUND, id.toString()));
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new EntryNotFoundException(USER_NOT_FOUND_ERROR, username));

        return modelMapper.map(user, UserDto.class);
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