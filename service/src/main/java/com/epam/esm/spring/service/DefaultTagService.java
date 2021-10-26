package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.TagDao;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<TagDto> findAll() {
        return tagDao.findAll().stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) {
        return tagDao.findById(id)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(EntryNotFoundException::new);
    }

    @Override
    public TagDto findByName(String name) {
        return tagDao.findByName(name)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(EntryNotFoundException::new);
    }

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
    public TagDto deleteById(long id) {
        Tag tagToBeDeleted = tagDao.findById(id)
                .orElseThrow(EntryNotFoundException::new);

        tagDao.delete(tagToBeDeleted);

        return modelMapper.map(tagToBeDeleted, TagDto.class);
    }

    @Override
    public boolean isExist(String name) {
        return tagDao.isExist(name);
    }
}
