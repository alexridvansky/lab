package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.CertificateDao;
import com.epam.esm.spring.repository.jdbc.dao.TagDao;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.CertificateParam;
import com.epam.esm.spring.repository.model.Pageable;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.CertificateDto;
import com.epam.esm.spring.service.dto.CertificateParamDto;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.util.PageRequestProcessor;
import com.epam.esm.spring.service.validator.SearchRequestValidator;
import org.apache.commons.compress.utils.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultCertificateServiceTest {
    private static final long FIRST_TAG_ID = 1;
    private static final long SECOND_TAG_ID = 2;

    private static final long FIRST_CERTIFICATE_ID = 1;
    private static final long NON_EXISTING_CERTIFICATE_ID = 99;

    private static final String FIRST_TAG_NAME = "fitness";
    private static final String SECOND_TAG_NAME = "food";

    private PageableDto defaultPageableDto = new PageableDto(0, 10);

    private Tag firstTag = Tag.builder()
            .id(FIRST_TAG_ID)
            .name(FIRST_TAG_NAME)
            .build();

    private Tag secondTag = Tag.builder()
            .id(SECOND_TAG_ID)
            .name(SECOND_TAG_NAME)
            .build();

    private TagDto firstTagDto = TagDto.builder()
            .id(FIRST_TAG_ID)
            .name(FIRST_TAG_NAME)
            .build();

    private TagDto secondTagDto = TagDto.builder()
            .id(SECOND_TAG_ID)
            .name(SECOND_TAG_NAME)
            .build();

    private Certificate firstCertificate = Certificate.builder()
            .id(1L)
            .name("First certificate")
            .description("First certificate description")
            .price(BigDecimal.valueOf(100))
            .duration(10)
            .tags(Sets.newHashSet(firstTag, secondTag))
            .build();

    private Certificate secondCertificate = Certificate.builder()
            .id(2L)
            .name("Second certificate")
            .description("Second certificate description")
            .price(BigDecimal.valueOf(200))
            .duration(20)
            .tags(Sets.newHashSet(firstTag))
            .build();

    private CertificateDto firstCertificateDto = CertificateDto.builder()
            .id(1L)
            .name("First certificate")
            .description("First certificate description")
            .price(BigDecimal.valueOf(100))
            .duration(10)
            .tags(Sets.newHashSet(firstTagDto, secondTagDto))
            .build();

    private CertificateDto secondCertificateDto = CertificateDto.builder()
            .id(2L)
            .name("Second certificate")
            .description("Second certificate description")
            .price(BigDecimal.valueOf(200))
            .duration(20)
            .tags(Sets.newHashSet(firstTagDto))
            .build();

    private final List<Certificate> certificates = Arrays.asList(firstCertificate, secondCertificate);

    private final List<Certificate> certificatesByParams = Collections.singletonList(secondCertificate);

    private final List<CertificateDto> certificateDtos = Arrays.asList(firstCertificateDto, secondCertificateDto);

    private final List<CertificateDto> certificatesDtoByParam = Collections.singletonList(secondCertificateDto);

    private final Set<String> tagNames = Collections.singleton("fitness");

    private final CertificateParam param = CertificateParam.builder()
            .tags(tagNames)
            .search("Second")
            .build();

    private final CertificateParamDto paramDto = CertificateParamDto.builder()
            .tags(tagNames)
            .search("Second")
            .build();

    private final Pageable defaultPageable = Pageable.builder()
            .page(0)
            .size(10)
            .build();

    private final Page<CertificateDto> expectedPageByParam =
            new Page<>(certificatesDtoByParam, defaultPageableDto, 0L);

    private final Page<CertificateDto> expectedPage =
            new Page<>(certificateDtos, defaultPageableDto, 0L);

    @InjectMocks
    private DefaultCertificateService certificateService;

    @Mock
    private DefaultTagService tagService;

    @Mock
    private TagDao tagDao;

    @Mock
    private CertificateDao certificateDao;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SearchRequestValidator searchRequestValidator;

    @Mock
    private PageRequestProcessor pageRequestProcessor;

    @BeforeEach
    void prepare() {
        lenient().when(modelMapper.map(firstTag, TagDto.class)).thenReturn(firstTagDto);
        lenient().when(modelMapper.map(secondTag, TagDto.class)).thenReturn(secondTagDto);
        lenient().when(modelMapper.map(secondTagDto, Tag.class)).thenReturn(secondTag);
        lenient().when(modelMapper.map(firstCertificate, CertificateDto.class)).thenReturn(firstCertificateDto);
        lenient().when(modelMapper.map(firstCertificateDto, Certificate.class)).thenReturn(firstCertificate);
        lenient().when(modelMapper.map(secondCertificate, CertificateDto.class)).thenReturn(secondCertificateDto);
        lenient().when(modelMapper.map(secondCertificateDto, Certificate.class)).thenReturn(secondCertificate);
        lenient().when(modelMapper.map(paramDto, CertificateParam.class)).thenReturn(param);
        lenient().when(modelMapper.map(defaultPageableDto, Pageable.class)).thenReturn(defaultPageable);
    }

    @Test
    void findAll() {
        when(certificateDao.findAll(defaultPageable)).thenReturn(certificates);
        Page<CertificateDto> actuals = certificateService.findAll(defaultPageableDto);
        assertEquals(expectedPage, actuals);
    }

    @Test
    void findBy() {
        when(certificateDao.findBy(param, defaultPageable)).thenReturn(certificatesByParams);
        Page<CertificateDto> actual = certificateService.findBy(paramDto, defaultPageableDto);
        assertEquals(expectedPageByParam, actual);
    }

    @Test
    void findById() {
        when(certificateDao.findById(FIRST_CERTIFICATE_ID)).thenReturn(Optional.of(firstCertificate));
        CertificateDto actual = certificateService.findById(FIRST_CERTIFICATE_ID);
        assertEquals(firstCertificateDto, actual);
    }

    @Test
    void findByIdExpectedException() {
        when(certificateDao.findById(NON_EXISTING_CERTIFICATE_ID)).thenReturn(Optional.empty());
        assertThrowsExactly(EntryNotFoundException.class, () -> certificateService.findById(NON_EXISTING_CERTIFICATE_ID));
    }

    @Test
    void insert() {
        when(certificateDao.insert(firstCertificate)).thenReturn(firstCertificate);
        CertificateDto actual = certificateService.insert(firstCertificateDto);
        assertEquals(firstCertificateDto, actual);
    }

    @Test
    void update() {
        when(certificateDao.insert(secondCertificate)).thenReturn(secondCertificate);
        CertificateDto actual = certificateService.insert(secondCertificateDto);
        assertEquals(secondCertificateDto, actual);
    }

    @Test
    void deleteById() {
        when(certificateDao.findById(FIRST_CERTIFICATE_ID)).thenReturn(Optional.of(firstCertificate));
        CertificateDto actual = certificateService.deleteById(FIRST_CERTIFICATE_ID);
        assertEquals(firstCertificateDto, actual);
    }

    @Test
    void deleteByIdExceptionExpected() {
        when(certificateDao.findById(NON_EXISTING_CERTIFICATE_ID)).thenReturn(Optional.empty());
        assertThrowsExactly(EntryNotFoundException.class, () -> certificateService.deleteById(NON_EXISTING_CERTIFICATE_ID));
    }
}