package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.config.TestConfigJpa;
import com.epam.esm.spring.repository.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfigJpa.class})
class DefaultTagDaoTest {
    private static final long ONE = 1L;
    private static final long TWO = 2L;
    private static final long THREE = 3L;
    private static final long FOUR = 4L;
    private static final long FIVE = 5L;
    private static final long SIX = 6L;
    private static final long SEVEN = 7L;
    private static final long EIGHT = 8L;
    private static final long NINE = 9L;
    private static final long TEN = 10L;
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

    @BeforeEach
    void setUp() {
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
        // Expected equality of given and provided lists
        List<Tag> actuals = tagDao.findAll();
        assertEquals(findAllExpected, actuals);
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("dataSet")
    void findById(long tagId, Tag tag) {
        // Providing existing Tags' IDs EQUALS are expected
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
    void findByNonExistingId(long tagId) {
        // Providing not existing Id Optional.empty is expected
        Optional<Tag> actual = tagDao.findById(tagId);
        assertEquals(Optional.empty(), actual);
    }

    @ParameterizedTest
    @MethodSource("dataSetOfNames")
    @Order(1)
    void findByName(String tagName, Tag tag) {
        // Providing existing Tags' names EQUALS are expected
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
    void findByNonExistingName() {
        // Providing not existing Tag name Optional.empty is expected
        Optional<Tag> actual = tagDao.findByName(TAG_TEN_NAME);
        assertEquals(Optional.empty(), actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT})
    @Order(1)
    void isExistWithExistingId(Long tagId) {
        // Providing existing Tags' IDs TRUE is expected
        boolean actual = tagDao.isExist(tagId);
        assertTrue(actual);
    }

    @Test
    @Order(1)
    void isExistWithNotExistingId() {
        // Providing not existing Tag ID false is expected
        boolean actual = tagDao.isExist(TEN);
        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT})
    @Order(1)
    void isUsed(Long tagId) {
        // Providing Tags' IDs which are associated with any certificates expected TRUE
        boolean actual = tagDao.isExist(tagId);
        assertTrue(actual);
    }

    @Test
    @Order(3)
    @Transactional
    void deleteById() {
        // Trying to delete existing Tag exception aren't expected
        tagDao.delete(tag_one);
    }

//    @Test
//    @Order(3)
//    void deleteByNonExistingId() {
//        // Trying to delete NOT_existing Tag FALSE is expected
//        boolean actual = tagDao.delete(15);
//        assertFalse(actual);
//    }

    @Test
    @Order(4)
    @Transactional
    void insert() {
        // Trying to add new Tag we are expecting to get the same tag back
        Tag newTag = Tag.builder().id(11L).name("somename").build();
        Tag actual = tagDao.insert(newTag);
        assertEquals(newTag, actual);
    }

    @Test
    @Order(4)
    @Transactional
    void insertAlreadyExistingEntryExceptionExpected() {
        // Inserting Tag which is already in the DB we exception is expected
        assertThrowsExactly(DuplicateKeyException.class, () -> tagDao.insert(tag_five));
    }
}