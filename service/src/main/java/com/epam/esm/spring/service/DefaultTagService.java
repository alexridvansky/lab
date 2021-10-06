package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.TagDao;
import com.epam.esm.spring.repository.model.Tag;
import com.epam.esm.spring.service.converter.DtoToTagConverter;
import com.epam.esm.spring.service.converter.TagToDtoConverter;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryInUseException;
import com.epam.esm.spring.service.exception.EntryNonValidNameException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultTagService implements TagService {
    private final TagDao tagDao;
    private final TagToDtoConverter tagToDtoConverter;
    private final DtoToTagConverter dtoToTagConverter;

    @Autowired
    public DefaultTagService(TagDao tagDao,
                             TagToDtoConverter tagToDtoConverter,
                             DtoToTagConverter dtoToTagConverter) {
        this.tagDao = tagDao;
        this.tagToDtoConverter = tagToDtoConverter;
        this.dtoToTagConverter = dtoToTagConverter;
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream()
                .map(tagToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) throws EntryNotFoundException {
        return tagDao.findById(id)
                .map(tagToDtoConverter::convert)
                .orElseThrow(EntryNotFoundException::new);
    }

    @Override
    public TagDto findByName(String name) throws EntryNotFoundException {
        return tagDao.findByName(name)
                .map(tagToDtoConverter::convert)
                .orElseThrow(EntryNotFoundException::new);

    }

    @Override
    public TagDto insert(TagDto tagDto) {
        if (tagDao.isExists(tagDto.getName())) {
            throw new EntryAlreadyExistsException();
        } else if (tagDto.getName() == null || tagDto.getName().isEmpty()) {
            throw new EntryNonValidNameException();
        }

        return tagToDtoConverter.convert(tagDao.insert(dtoToTagConverter.convert(tagDto)));
    }

    @Override
    public TagDto deleteById(long id) {
        Tag tagToBeDeleted = tagDao.findById(id)
                .orElseThrow(EntryNotFoundException::new);

        if (tagDao.isUsed(id)) {
            throw new EntryInUseException();
        }

        tagDao.deleteById(id);

        return tagToDtoConverter.convert(tagToBeDeleted);
    }

    @Override
    public boolean isExists(String name) {
        return tagDao.isExists(name);
    }
}
