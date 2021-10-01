package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.TagDao;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.service.converter.TagToDtoConverter;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultTagService implements TagService {
    private final TagDao tagDao;
    private final TagToDtoConverter tagToDtoConverter;

    @Autowired
    public DefaultTagService(TagDao tagDao, TagToDtoConverter tagToDtoConverter) {
        this.tagDao = tagDao;
        this.tagToDtoConverter = tagToDtoConverter;
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream()
                .map(tagToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) {
        return tagDao.findById(id)
                .map(tagToDtoConverter::convert)
                .orElseThrow(EntryNotFoundException::new);
    }

    @Override
    public Tag insert(Tag tag) {
        return tagDao.insert(tag);
    }
}
