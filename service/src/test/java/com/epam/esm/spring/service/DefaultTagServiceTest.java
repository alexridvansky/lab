package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.TagDao;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.converter.DtoToTagConverter;
import com.epam.esm.spring.service.converter.TagToDtoConverter;
import com.epam.esm.spring.service.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultTagServiceTest {

    private static final long FIRST_TAG_ID = 1;
    private static final long SECOND_TAG_ID = 2;
    private static final long NEW_TAG_ID = 12;
    private static final String FIRST_TAG_NAME = "fitness";
    private static final String SECOND_TAG_NAME = "food";
    private static final String NEW_TAG_NAME = "quest";
    private TagDto firstTagDto;
    private TagDto secondTagDto;
    private Tag firstTag;
    private Tag secondTag;
    private List<TagDto> tagsDto;
    private List<Tag> tags;

    @InjectMocks
    private DefaultTagService tagService;

    @Mock
    private TagDao tagDao;

    @Mock
    private TagToDtoConverter tagToDtoConverter;

    @Mock
    private DtoToTagConverter dtoToTagConverter;

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

        firstTag = Tag.builder()
                .id(FIRST_TAG_ID)
                .name(FIRST_TAG_NAME)
                .build();

        secondTag = Tag.builder()
                .id(SECOND_TAG_ID)
                .name(SECOND_TAG_NAME)
                .build();

        tagsDto = new ArrayList<>();
        tagsDto.add(firstTagDto);
        tagsDto.add(secondTagDto);

        tags = new ArrayList<>();
        tags.add(firstTag);
        tags.add(secondTag);

        when(tagDao.findAll()).thenReturn(tags);
        when(tagDao.findById(FIRST_TAG_ID)).thenReturn(Optional.of(firstTag));
    }

    @Test
    void findAll() {
        List<TagDto> actualDtoList = tagService.findAll();

        assertEquals(tagsDto, actualDtoList);
    }

    @Test
    void findById() {
        TagDto actual = tagService.findById(FIRST_TAG_ID);

        assertEquals(firstTagDto, actual);
    }

    @Test
    void findByName() {
    }

    @Test
    void insert() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void exists() {
    }
}