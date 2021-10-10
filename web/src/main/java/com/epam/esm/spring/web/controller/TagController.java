package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.TagService;
import com.epam.esm.spring.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller provides service within Tag.class entities.
 */
@RestController
@Validated
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Is used for getting tag by ID
     *
     * @return TagDto
     */
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    /**
     * Is used for getting list of tags available
     *
     * @return List<TagDto> the list of certificates
     */
    @GetMapping()
    public List<TagDto> findAll() {
        return tagService.findAll();
    }

    /**
     * Is used for inserting new Tag
     *
     * @return TagDto just inserted
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto insert(@Valid @RequestBody TagDto tagDto) {
        return tagService.insert(tagDto);
    }

    /**
     * Is used for Removing Tag by ID given
     *
     * @param id the id of Tag to remove
     */
    @DeleteMapping("/{id}")
    public TagDto deleteTag(@PathVariable Long id) {
        return tagService.deleteById(id);
    }
}
