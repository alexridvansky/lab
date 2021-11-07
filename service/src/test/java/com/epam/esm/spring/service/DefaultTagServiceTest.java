package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.TagDao;
import com.epam.esm.spring.repository.model.Pageable;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultTagServiceTest {

    private static final long FIRST_TAG_ID = 1;
    private static final long SECOND_TAG_ID = 2;
    private static final long THIRD_TAG_ID = 3;
    private static final long NON_EXISTING_TAG_ID = 44;
    private static final String FIRST_TAG_NAME = "fitness";
    private static final String SECOND_TAG_NAME = "food";
    private static final String THIRD_TAG_NAME = "quest";
    private Pageable defaultPageable;
    private TagDto firstTagDto;
    private TagDto secondTagDto;
    private TagDto thirdTagDto;
    private Tag firstTag;
    private Tag secondTag;
    private Tag thirdTag;
    private List<TagDto> tagsDto;
    private List<TagDto> tagsDtoAfterDelete;
    private List<Tag> tags;
    private List<Tag> tagsAfterDelete;

    @InjectMocks
    private DefaultTagService tagService;

    @Mock
    private TagDao tagDao;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void prepare() {
        firstTagDto = TagDto.builder()
                .id(FIRST_TAG_ID)
                .name(FIRST_TAG_NAME)
                .build();

        secondTagDto = TagDto.builder()
                .id(SECOND_TAG_ID)
                .name(SECOND_TAG_NAME)
                .build();

        thirdTagDto = TagDto.builder()
                .id(THIRD_TAG_ID)
                .name(THIRD_TAG_NAME)
                .build();

        firstTag = Tag.builder()
                .id(FIRST_TAG_ID)
                .name(FIRST_TAG_NAME)
                .build();

        secondTag = Tag.builder()
                .id(SECOND_TAG_ID)
                .name(SECOND_TAG_NAME)
                .build();

        thirdTag = Tag.builder()
                .id(THIRD_TAG_ID)
                .name(THIRD_TAG_NAME)
                .build();

        tagsDto = new ArrayList<>();
        tagsDto.add(firstTagDto);
        tagsDto.add(secondTagDto);

        tagsDtoAfterDelete = new ArrayList<>();
        tagsDtoAfterDelete.add(firstTagDto);

        tags = new ArrayList<>();
        tags.add(firstTag);
        tags.add(secondTag);

        tagsAfterDelete = new ArrayList<>();
        tagsAfterDelete.add(firstTag);

        defaultPageable = Pageable.builder()
                .page(0)
                .size(10)
                .build();

        lenient().when(modelMapper.map(firstTag, TagDto.class)).thenReturn(firstTagDto);
        lenient().when(modelMapper.map(secondTag, TagDto.class)).thenReturn(secondTagDto);
        lenient().when(modelMapper.map(thirdTag, TagDto.class)).thenReturn(thirdTagDto);
        lenient().when(modelMapper.map(thirdTagDto, Tag.class)).thenReturn(thirdTag);
        lenient().when(modelMapper.map(secondTagDto, Tag.class)).thenReturn(secondTag);
    }

    @Test
    void findAll() {
        when(tagDao.findAll(defaultPageable)).thenReturn(tags);
        Page<TagDto> actualDtoList = tagService.findAll(new PageableDto(0, 100));
        assertEquals(tagsDto, actualDtoList);
    }

    @Test
    void findById() {
        when(tagDao.findById(FIRST_TAG_ID)).thenReturn(Optional.of(firstTag));
        TagDto actual = tagService.findById(FIRST_TAG_ID);
        assertEquals(firstTagDto, actual);
    }

    @Test
    void findByIdExceptionExpected() {
        when(tagDao.findById(NON_EXISTING_TAG_ID)).thenReturn(Optional.empty());
        assertThrowsExactly(EntryNotFoundException.class, () -> tagService.findById(NON_EXISTING_TAG_ID));
    }

    @Test
    void testFindByName() {
        when(tagDao.findByName(FIRST_TAG_NAME)).thenReturn(Optional.of(firstTag));
        TagDto actual = tagService.findByName(FIRST_TAG_NAME);
        assertEquals(firstTagDto, actual);
    }

    @Test
    void insert() {
        when(tagDao.insert(thirdTag)).thenReturn(thirdTag);
        TagDto actual = tagService.insert(thirdTagDto);
        assertEquals(thirdTagDto, actual);
    }

    @Test
    void deleteById() {
        when(tagDao.findById(SECOND_TAG_ID)).thenReturn(Optional.of(secondTag));
        TagDto actual = tagService.deleteById(SECOND_TAG_ID);
        assertEquals(secondTagDto, actual);
    }

    @Test
    void exists() {
        when(tagDao.isExist(SECOND_TAG_NAME)).thenReturn(true);
        boolean actual = tagService.isExist(SECOND_TAG_NAME);
        assertTrue(actual);
    }
}