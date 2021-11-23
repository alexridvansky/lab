package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.jparepository.UserRepository;
import com.epam.esm.spring.repository.model.Role;
import com.epam.esm.spring.repository.model.User;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.UserResponseDto;
import com.epam.esm.spring.service.dto.UserRequestDto;
import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.util.PageRequestProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_USER_NOT_FOUND;

@Service
public class JpaUserService {

    private static final String USER_NOT_FOUND_ERROR = "error.user_not_found";
    private static final long DEFAULT_USER_ROLE_ID = 2L;
    private static final String DEFAULT_USER_ROLE_NAME = "ROLE_USER";
    private static final Role DEFAULT_USER_ROLE = new Role(DEFAULT_USER_ROLE_ID, DEFAULT_USER_ROLE_NAME);
    private static final boolean DEFAULT_USER_STATE_ACTIVE = true;

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

    public Page<UserResponseDto> findAll(PageableDto pageRequest) {
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

    public UserResponseDto findById(Long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserResponseDto.class))
                .orElseThrow(() -> new EntryNotFoundException(ERROR_USER_NOT_FOUND, id.toString()));
    }

    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new EntryNotFoundException(USER_NOT_FOUND_ERROR, username));

        return modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto insert(UserRequestDto userRequestDto) {
        Optional<User> userExistenceCheck = userRepository.findByUsername(userRequestDto.getUsername());

        if (userExistenceCheck.isPresent()) {
            throw new EntryAlreadyExistsException(userRequestDto.getUsername());
        }

        User userToSave = modelMapper.map(userRequestDto, User.class);
        userToSave.setRole(DEFAULT_USER_ROLE);
        userToSave.setActive(DEFAULT_USER_STATE_ACTIVE);
        userToSave.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        userToSave = userRepository.save(userToSave);

        return modelMapper.map(userToSave, UserResponseDto.class);
    }

    public UserResponseDto deleteById(Long id) {
        throw new UnsupportedOperationException();
    }
}