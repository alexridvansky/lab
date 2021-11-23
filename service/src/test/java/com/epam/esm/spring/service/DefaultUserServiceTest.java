package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.DefaultUserDao;
import com.epam.esm.spring.repository.model.Pageable;
import com.epam.esm.spring.repository.model.User;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.UserDto;
import com.epam.esm.spring.service.util.PageRequestProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    private static final Long ID_ONE = 1L;
    private static final Long ID_TWO = 2L;
    private static final Long ID_THREE = 3L;

    private static final String USER_ONE_NAME = "serj_2000";
    private static final String USER_TWO_NAME = "party";
    private static final String USER_THREE_NAME = "pascal_forever";

    private static final Long NUMBER_OF_ENTRIES_TOTAL = 3L;

    private static final Pageable defaultPageable;
    private static final PageableDto defaultPageableDto;
    private static final User userOne;
    private static final User userTwo;
    private static final User userThree;
    private static final UserDto userDtoOne;
    private static final UserDto userDtoTwo;
    private static final UserDto userDtoThree;
    private static final List<User> users;
    private static final List<UserDto> usersDtoExpected;
    private static final Page<UserDto> expectedPage;

    static {
        defaultPageable = new Pageable(0, 10);

        defaultPageableDto = new PageableDto(0, 10);

        userOne = User.builder()
                .id(1L)
                .firstname("Sergey")
                .lastname("Petrow")
                .username(USER_ONE_NAME)
                .password("serjpetrpass1")
                .build();

        userTwo = User.builder()
                .id(2L)
                .firstname("Anton")
                .lastname("Tarakanow")
                .username(USER_TWO_NAME)
                .password("longPASS_1")
                .build();

        userThree = User.builder()
                .id(3L)
                .firstname("Pasha")
                .lastname("Rubin")
                .username(USER_THREE_NAME)
                .password("iL0vep@scal")
                .build();

        userDtoOne = UserDto.builder()
                .id(1L)
                .firstname("Sergey")
                .lastname("Petrow")
                .username(USER_ONE_NAME)
                .build();

        userDtoTwo = UserDto.builder()
                .id(2L)
                .firstname("Anton")
                .lastname("Tarakanow")
                .username(USER_TWO_NAME)
                .build();

        userDtoThree = UserDto.builder()
                .id(3L)
                .firstname("Pasha")
                .lastname("Rubin")
                .username(USER_THREE_NAME)
                .build();

        users = Arrays.asList(userOne, userTwo, userThree);

        usersDtoExpected = Arrays.asList(userDtoOne, userDtoTwo, userDtoThree);

        expectedPage = new Page<>(usersDtoExpected, defaultPageableDto, NUMBER_OF_ENTRIES_TOTAL);
    }

    @BeforeEach
    void prepare() {
        lenient().when(modelMapper.map(userOne, UserDto.class)).thenReturn(userDtoOne);
        lenient().when(modelMapper.map(userTwo, UserDto.class)).thenReturn(userDtoTwo);
        lenient().when(modelMapper.map(userThree, UserDto.class)).thenReturn(userDtoThree);
        lenient().when(modelMapper.map(defaultPageableDto, Pageable.class)).thenReturn(defaultPageable);
        lenient().when(userDao.countEntry()).thenReturn(NUMBER_OF_ENTRIES_TOTAL);
    }

    @InjectMocks
    private DefaultUserService userService;

    @Mock
    private DefaultUserDao userDao;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PageRequestProcessor requestProcessor;

    @Test
    void findAll() {
        when(userDao.findAll(defaultPageable)).thenReturn(users);
        Page<UserDto> actualPageUsers = userService.findAll(defaultPageableDto);
        assertEquals(expectedPage, actualPageUsers);
    }

    @ParameterizedTest
    @MethodSource("setOfUsersById")
    void findById(Long userIdRequested, User userToMock, UserDto userDtoExpected) {
        when(userDao.findById(userIdRequested)).thenReturn(Optional.of(userToMock));
        UserDto actualUserDto = userService.findById(userIdRequested);
        assertEquals(userDtoExpected, actualUserDto);
    }

    private static Stream<Arguments> setOfUsersById() {
        return Stream.of(
                arguments(ID_ONE, userOne, userDtoOne),
                arguments(ID_TWO, userTwo, userDtoTwo),
                arguments(ID_THREE, userThree, userDtoThree)
        );
    }

    @ParameterizedTest
    @MethodSource("setOfUsersByName")
    void findByUsername(String userNameToLookFor, User userToMock, UserDto userDtoExpected) {
        when(userDao.findByUsername(userNameToLookFor)).thenReturn(Optional.of(userToMock));
        UserDto actualUserDto = userService.findByUsername(userNameToLookFor);
        assertEquals(userDtoExpected, actualUserDto);
    }

    private static Stream<Arguments> setOfUsersByName() {
        return Stream.of(
                arguments(USER_ONE_NAME, userOne, userDtoOne),
                arguments(USER_TWO_NAME, userTwo, userDtoTwo),
                arguments(USER_THREE_NAME, userThree, userDtoThree)
        );
    }
}