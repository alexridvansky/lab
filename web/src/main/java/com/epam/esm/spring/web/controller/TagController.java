package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.TagService;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.web.hateoas.LinkBuilder;
import com.epam.esm.spring.web.hateoas.TagLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@PropertySource("classpath:pagination.properties")
@RequestMapping(value = "/api/tags")
public class TagController implements Controller<TagDto> {

    private final TagService tagService;
    private final LinkBuilder<TagDto> linkBuilder;

    @Value("${offset-default}")
    private String offsetDefault;
    @Value("${limit-default}")
    private String limitDefault;
    @Value("${limit-min}")
    private String limitMin;
    @Value("${limit-max}")
    private String limitMax;

    @Autowired
    public TagController(TagService tagService,
                         TagLinkBuilder linkBuilder) {
        this.tagService = tagService;
        this.linkBuilder = linkBuilder;
    }

    @Override
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        TagDto tag = tagService.findById(id);
        linkBuilder.addRemoveLink(tag);
        linkBuilder.addFindAllLink(tag);

        return tag;
    }

    @Override
    @GetMapping()
    public List<TagDto> findAll() {
        return tagService.findAll().stream()
                .map(linkBuilder::addFindByIdLink)
                .map(linkBuilder::addRemoveLink)
                .collect(Collectors.toList());
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto insert(@RequestBody TagDto tagDto) {
        return tagService.insert(tagDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        tagService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
