package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.TagDao;
import com.epam.esm.spring.repository.model.PageParam;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.Pageable;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_TAG_NOT_FOUND;
import static com.epam.esm.spring.service.exception.ErrorMessage.ID;
import static com.epam.esm.spring.service.exception.ErrorMessage.NAME;

@Service
public class DefaultTagService implements TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public DefaultTagService(TagDao tagDao,
                             ModelMapper modelMapper) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<TagDto> findAll(Pageable pageRequest) {
        List<TagDto> result = tagDao.findAll(modelMapper.map(pageRequest, PageParam.class)).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());

        Long totalPages = tagDao.countEntry();

        return new Page<>(result, pageRequest, totalPages);
    }

    @Override
    public TagDto findById(Long id) {
        return tagDao.findById(id)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new EntryNotFoundException(ERROR_TAG_NOT_FOUND, ID + id.toString()));
    }

    @Override
    public TagDto findByName(String name) {
        return tagDao.findByName(name)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new EntryNotFoundException(ERROR_TAG_NOT_FOUND, NAME + name));
    }

    @Transactional
    @Override
    public TagDto insert(TagDto tagDto) {
        if (tagDao.isExist(tagDto.getName())) {
            throw new EntryAlreadyExistsException(tagDto.getName());
        }

        return modelMapper.map(tagDao.insert(modelMapper.map(tagDto, Tag.class)), TagDto.class);
    }

    @Override
    public List<Tag> processTagList(List<Tag> tags) {
        return tags.stream()
                .map(tag -> tagDao.findByName(tag.getName()).orElseGet(() -> tagDao.insert(tag)))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TagDto deleteById(Long id) {
        Tag tagToBeDeleted = tagDao.findById(id)
                .orElseThrow(() -> new EntryNotFoundException(ERROR_TAG_NOT_FOUND, ID + id.toString()));

        tagDao.delete(tagToBeDeleted);

        return modelMapper.map(tagToBeDeleted, TagDto.class);
    }

    @Override
    public boolean isExist(String name) {
        return tagDao.isExist(name);
    }

    @Override
    public TagDto findMostUsed() {
        return modelMapper.map(tagDao.findMostUsed(), TagDto.class);
    }
}
