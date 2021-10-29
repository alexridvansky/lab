package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.TagService;
import com.epam.esm.spring.service.dto.AbstractDto;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.web.hateoas.TagLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller provides service within Tag entities.
 */
@RestController
@Validated
@PropertySource("classpath:pagination.properties")
@RequestMapping(value = "/api/tags")
public class TagController implements Controller<AbstractDto> {

    @Value("${offset-default}")
    private String offsetDefault;
    @Value("${limit-default}")
    private String limitDefault;
    @Value("${limit-min}")
    private String limitMin;
    @Value("${limit-max}")
    private String limitMax;

    private final TagService tagService;
    private final TagLinkBuilder tagLinkBuilder;

    @Autowired
    public TagController(TagService tagService,
                         TagLinkBuilder tagLinkBuilder) {
        this.tagService = tagService;
        this.tagLinkBuilder = tagLinkBuilder;
    }

    /**
     * Is used for getting tag by ID
     *
     * @return TagDto
     */
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable @Positive Long id) {
        TagDto tag = tagService.findById(id);
        tagLinkBuilder.addRemoveLink(tag);

        return tagService.findById(id);
    }

    /**
     * Is used for getting list of tags available
     *
     * @return List<TagDto> the list of certificates
     */
    @GetMapping()
    public List<TagDto> findAll() {
        return tagService.findAll().stream()
                .map(tagLinkBuilder::addRemoveLink)
                .collect(Collectors.toList());
    }

    /**
     * Is used for inserting new Tag
     *
     * @return TagDto just inserted
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto insert(@Valid @RequestBody TagDto tagDto) {
        return tagService.insert(tagDto);
    }

    /**
     * Is used for Removing Tag by ID given
     *
     * @param id the id of Tag to remove
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable @Positive Long id) {
        tagService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
