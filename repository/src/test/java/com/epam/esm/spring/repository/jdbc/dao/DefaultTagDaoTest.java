package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.config.TestConfig;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class DefaultTagDaoTest {
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int EIGHT = 8;
    private static final int NINE = 9;
    private static final int TEN = 10;
    private static final String TAG_ONE_NAME = "food";
    private static final String TAG_TWO_NAME = "bbq";
    private static final String TAG_TEN_NAME = "non_existing";
    private static List<Tag> findAllExpected;
    private static Tag tag_one;
    private static Tag tag_two;
    private static Tag tag_three;
    private static Tag tag_four;
    private static Tag tag_five;
    private static Tag tag_six;
    private static Tag tag_seven;
    private static Tag tag_eight;
    private static Tag tag_nine;
    private final DefaultTagDao tagDao;

    @Autowired
    DefaultTagDaoTest(DefaultTagDao tagDao) {
        this.tagDao = tagDao;
    }

    static {
        tag_one = Tag.builder()
                .id(1L)
                .name("food")
                .build();

        tag_two = Tag.builder()
                .id(2L)
                .name("bbq")
                .build();

        tag_three = Tag.builder()
                .id(3L)
                .name("beer")
                .build();

        tag_four = Tag.builder()
                .id(4L)
                .name("sauna")
                .build();

        tag_five = Tag.builder()
                .id(5L)
                .name("gym")
                .build();

        tag_six = Tag.builder()
                .id(6L)
                .name("cardio")
                .build();

        tag_seven = Tag.builder()
                .id(7L)
                .name("health")
                .build();

        tag_eight = Tag.builder()
                .id(8L)
                .name("fitness")
                .build();

        tag_nine = Tag.builder()
                .id(9L)
                .name("fishing")
                .build();

        findAllExpected = Arrays.asList(tag_one, tag_two, tag_three, tag_four, tag_five, tag_six, tag_seven, tag_eight,
                tag_nine);
    }

    @Test
    @Order(1)
    void findAll() {
        List<Tag> actuals = tagDao.findAll();

        assertEquals(findAllExpected, actuals);
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("dataSet")
    void findById(int tagId, Tag tag) {
        Optional<Tag> actual = tagDao.findById(tagId);

        assertEquals(tag, actual.get());
    }

    private static Stream<Arguments> dataSet() {
        return Stream.of(
                arguments(ONE, tag_one),
                arguments(TWO, tag_two),
                arguments(THREE, tag_three),
                arguments(FOUR, tag_four),
                arguments(FIVE, tag_five)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 13, 15, 30, 150, Integer.MAX_VALUE})
    @Order(1)
    void findByIdNonExistingId(int tagId) {
        Optional<Tag> actual = tagDao.findById(tagId);

        assertEquals(Optional.empty(), actual);
    }

    @ParameterizedTest
    @MethodSource("dataSetOfNames")
    @Order(1)
    void findByNameOne(String tagName, Tag tag) {
        Optional<Tag> actual = tagDao.findByName(tagName);

        assertEquals(tag, actual.get());
    }

    private static Stream<Arguments> dataSetOfNames() {
        return Stream.of(
                arguments(TAG_ONE_NAME, tag_one),
                arguments(TAG_TWO_NAME, tag_two)
        );
    }

    @Test
    @Order(1)
    void findByNameNonExisting() {
        Optional<Tag> actual = tagDao.findByName(TAG_TEN_NAME);

        assertEquals(Optional.empty(), actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT})
    @Order(1)
    void isExistTagOne(int tagId) {
        boolean actual = tagDao.isExist(tagId);

        assertTrue(actual);
    }

    @Test
    @Order(1)
    void isExistTagThreeNonExisting() {
        boolean actual = tagDao.isExist(TEN);

        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT})
    @Order(1)
    void isUsedTagOne(int tagId) {
        boolean actual = tagDao.isExist(tagId);

        assertTrue(actual);
    }

    @Test
    @Order(2)
    void isUsedTagNine() {
        boolean actual = tagDao.isUsed(NINE);

        assertFalse(actual);
    }

    @Test
    @Order(3)
    void deleteByIdOne() {
        boolean actual = tagDao.deleteById(ONE);

        assertTrue(actual);
    }

    @Test
    @Order(3)
    void deleteByIdTenNonExisting() {
        boolean actual = tagDao.deleteById(TEN);

        assertFalse(actual);
    }

    @Test
    void insert() {
    }
}