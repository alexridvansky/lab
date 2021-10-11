package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.config.TestConfig;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class DefaultTagDaoTest {
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int NINE = 0;
    private static final int TEN = 10;
    private static final String TAG_ONE_NAME = "food";
    private static final String TAG_TWO_NAME = "bbq";
    private static final String TAG_TEN_NAME = "non_existing";
    private final DefaultTagDao tagDao;
    private List<Tag> findAllExpected;
    private Tag tag_one;
    private Tag tag_two;
    private Tag tag_three;
    private Tag tag_four;
    private Tag tag_five;
    private Tag tag_six;
    private Tag tag_seven;
    private Tag tag_eight;
    private Tag tag_nine;

    @Autowired
    DefaultTagDaoTest(DefaultTagDao tagDao) {
        this.tagDao = tagDao;
    }

    @BeforeEach
    void prepare() {
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
    void insert() {
    }

    @Test
    void findAll() {
        List<Tag> actuals = tagDao.findAll();

        assertEquals(findAllExpected, actuals);
    }

    @Test
    void findByIdOne() {
        Optional<Tag> actual = tagDao.findById(ONE);

        assertEquals(tag_one, actual.get());
    }

    @Test
    void findByIdThree() {
        Optional<Tag> actual = tagDao.findById(THREE);

        assertEquals(tag_three, actual.get());
    }

    @Test
    void findByIdNonExistingId() {
        Optional<Tag> actual = tagDao.findById(TEN);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void findByNameOne() {
        Optional<Tag> actual = tagDao.findByName(TAG_ONE_NAME);

        assertEquals(tag_one, actual.get());
    }

    @Test
    void findByNameTwo() {
        Optional<Tag> actual = tagDao.findByName(TAG_TWO_NAME);

        assertEquals(tag_two, actual.get());
    }

    @Test
    void findByNameNonExisting() {
        Optional<Tag> actual = tagDao.findByName(TAG_TEN_NAME);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void isExistTagOne() {
        boolean actual = tagDao.isExist(ONE);

        assertTrue(actual);
    }

    @Test
    void isExistTagThree() {
        boolean actual = tagDao.isExist(THREE);

        assertTrue(actual);
    }

    @Test
    void isExistTagThreeNonExisting() {
        boolean actual = tagDao.isExist(TEN);

        assertFalse(actual);
    }

    @Test
    void isUsedTagOne() {
        boolean actual = tagDao.isExist(ONE);

        assertTrue(actual);
    }

    @Test
    void isUsedTagThree() {
        boolean actual = tagDao.isExist(THREE);

        assertTrue(actual);
    }

    @Test
    void isUsedTagNine() {
        boolean actual = tagDao.isExist(NINE);

        assertFalse(actual);
    }

    @Test
    void deleteByIdOne() {
        boolean actual = tagDao.deleteById(ONE);

        assertTrue(actual);
    }

    @Test
    void deleteByIdNineNonExisting() {
        boolean actual = tagDao.deleteById(NINE);

        assertFalse(actual);
    }
}