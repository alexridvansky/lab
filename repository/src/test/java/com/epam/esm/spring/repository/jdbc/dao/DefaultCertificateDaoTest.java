package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.config.TestConfig;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class DefaultCertificateDaoTest {
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int TEN = 10;
    private final DefaultCertificateDao certificateDao;
    private List<Certificate> findAllExpected;
    private List<Certificate> findAllByParamsExpected;
    private Map<String, String> params;
    private Tag tag_one;
    private Tag tag_two;
    private Tag tag_three;
    private Tag tag_four;
    private Tag tag_five;
    private Tag tag_six;
    private Tag tag_seven;
    private Tag tag_eight;
    private Certificate cert_one;
    private Certificate cert_two;
    private Certificate cert_three;

    @Autowired
    DefaultCertificateDaoTest(DefaultCertificateDao certificateDao) {
        this.certificateDao = certificateDao;
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

        cert_one = Certificate.builder()
                .id(1L)
                .name("Beef and pork fall weekend party")
                .description("unlimited food for a couple of visitors")
                .price(BigDecimal.valueOf(50))
                .duration(2)
                .createDate(LocalDateTime.of(2019, 11, 17, 11, 11, 11))
                .lastUpdateDate(LocalDateTime.of(2021, 10, 18, 11, 11, 11))
                .tags(Arrays.asList(tag_one, tag_two))
                .build();

        cert_two = Certificate.builder()
                .id(2L)
                .name("Paulaner beer fest")
                .description("including entry tickets and food for group of 5")
                .price(BigDecimal.valueOf(150))
                .duration(1)
                .createDate(LocalDateTime.of(2019, 11, 17, 11, 11, 11))
                .lastUpdateDate(LocalDateTime.of(2021, 10, 18, 11, 11, 11))
                .tags(Arrays.asList(tag_three, tag_one, tag_two))
                .build();

        cert_three = Certificate.builder()
                .id(3L)
                .name("Royal fitness Club \"Be in shape\" month")
                .description("Unlimited access to all facilities")
                .price(BigDecimal.valueOf(100))
                .duration(30)
                .createDate(LocalDateTime.of(2019, 11, 17, 11, 11, 11))
                .lastUpdateDate(LocalDateTime.of(2021, 10, 18, 11, 11, 11))
                .tags(Arrays.asList(tag_four, tag_five, tag_six, tag_seven, tag_eight))
                .build();

        params = new HashMap<>();
        params.put("tag", "beer");
        params.put("search", "weekend");
        params.put("sort", "price");
        params.put("order", "asc");

        findAllExpected = Arrays.asList(cert_one, cert_two, cert_three);
        findAllByParamsExpected = Arrays.asList(cert_two, cert_one);
    }

    @Test
    void findAll() {
        List<Certificate> actuals = certificateDao.findAll();

        assertEquals(findAllExpected, actuals);
    }

    @Test
    void findAllByParam() {
        List<Certificate> actuals = certificateDao.findByParams(params);

        assertEquals(findAllByParamsExpected, actuals);
    }

    @Test
    void findByIdOne() {
        Optional<Certificate> actual = certificateDao.findById(ONE);

        assertEquals(cert_one, actual.get());
    }

    @Test
    void findByIdTwo() {
        Optional<Certificate> actual = certificateDao.findById(TWO);

        assertEquals(cert_two, actual.get());
    }

    @Test
    void findByIdTenOptionalEmptyExpected() {
        Optional<Certificate> actual = certificateDao.findById(TEN);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void insertDuplicateNameExceptionExpected() {
        assertThrowsExactly(DuplicateKeyException.class, () -> certificateDao.insert(cert_one));
    }

    @Test
    void isExist() {
        boolean actual = certificateDao.isExist(ONE);

        assertTrue(actual);
    }

    @Test
    void isExistNonExistingEntry() {
        boolean actual = certificateDao.isExist(TEN);

        assertFalse(actual);
    }

    @Test
    void deleteById() {
        boolean actual = certificateDao.deleteById(ONE);

        assertTrue(actual);
    }

    @Test
    void deleteByIdNonExistingEntryExpectedFalse() {
        boolean actual = certificateDao.deleteById(TEN);

        assertFalse(actual);
    }
}