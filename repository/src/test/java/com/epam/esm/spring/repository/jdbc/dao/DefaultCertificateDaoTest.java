package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.config.TestConfigJpa;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.CertificateParam;
import com.epam.esm.spring.repository.model.Pageable;
import com.epam.esm.spring.repository.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfigJpa.class})
class DefaultCertificateDaoTest {
    private static final long ONE = 1L;
    private static final long TWO = 2L;
    private static final long TEN = 10L;
    private static final String EXISTING_CERTIFICATE_NAME = "Beef and pork fall weekend party";
    private static final String NOT_CERTIFICATE_TAG_NAME = "Beer and vodka fall weekend party";
    private static Certificate cert_one;
    private static Certificate cert_two;
    private static Certificate cert_three;
    private static CertificateParam certificateParam;
    private static Pageable defaultPageable;
    private CertificateDao certificateDao;
    private Set<String> tagNames;
    private List<Certificate> findAllExpected;
    private List<Certificate> findByExpected;
    private Map<String, String> params;
    private Tag tag_one;
    private Tag tag_two;
    private Tag tag_three;
    private Tag tag_four;
    private Tag tag_five;
    private Tag tag_six;
    private Tag tag_seven;
    private Tag tag_eight;

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
                .tags(Arrays.asList(tag_one, tag_two, tag_three))
                .build();

        cert_two = Certificate.builder()
                .id(2L)
                .name("Paulaner beer fest")
                .description("including entry tickets and food for group of 5 for weekend")
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

        tagNames = Collections.singleton("fitness");

        certificateParam = CertificateParam.builder()
                .tags(tagNames)
                .search("Second")
                .build();

        findAllExpected = Arrays.asList(cert_one, cert_two, cert_three);
        findByExpected = Collections.singletonList(cert_one);

        defaultPageable = Pageable.builder()
                .page(0)
                .size(10)
                .build();
    }

    @Test
    @Order(1)
    void findAll() {
        // Expected equality of given and provided lists
        List<Certificate> actuals = certificateDao.findAll(defaultPageable);
        assertEquals(findAllExpected, actuals);
    }

    @Test
    @Order(1)
    void findBy() {
        // Asking the list of certificates by parameters given expected the following list
        List<Certificate> actuals = certificateDao.findBy(certificateParam, defaultPageable);
        assertEquals(findByExpected, actuals);
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("dataSet")
    void findById(long certificateId, Certificate certificate) {
        // Looking for a certificate with existing id this certificate is expected
        Optional<Certificate> actual = certificateDao.findById(ONE);
        assertEquals(cert_one, actual.get());
    }

    private static Stream<Arguments> dataSet() {
        return Stream.of(
                arguments(ONE, cert_one),
                arguments(TWO, cert_two)
        );
    }

    @Test
    @Order(1)
    void findByNonExistingIdOptionalEmptyExpected() {
        // Looking for a non existing ID optional.empty expected
        Optional<Certificate> actual = certificateDao.findById(TEN);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    @Order(2)
    @Transactional
    void insertDuplicateNameExceptionExpected() {
        // Trying to insert already existing certificate exception is expected
        assertThrowsExactly(JpaSystemException.class, () -> certificateDao.insert(cert_one));
    }

    @Test
    @Order(1)
    void isExist() {
        // Asking for an existing certificate ID TRUE is expected
        boolean actual = certificateDao.isExist(EXISTING_CERTIFICATE_NAME);
        assertTrue(actual);
    }

    @Test
    @Order(1)
    void isExistNonExistingEntry() {
        // Trying to check out whether not existing certificate exists FALSE is expected
        boolean actual = certificateDao.isExist(NOT_CERTIFICATE_TAG_NAME);
        assertFalse(actual);
    }

//    @Test
//    @Order(2)
//    void deleteById() {
//        // Deleting existing certificate TRUE is expected
//        boolean actual = certificateDao.deleteById(ONE);
//        assertTrue(actual);
//    }
//
//    @Test
//    @Order(2)
//    void deleteByIdNonExistingEntryExpectedFalse() {
//        // Deleting non existing certificate FALSE is expected
//        boolean actual = certificateDao.deleteById(TEN);
//        assertFalse(actual);
//    }
}