package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.TagService;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.web.hateoas.LinkBuilder;
import com.epam.esm.spring.web.hateoas.TagLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/tags")
public class TagController implements Controller<TagDto> {

    private final TagService tagService;
    private final LinkBuilder<TagDto> linkBuilder;

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
    public Page<TagDto> findAll(@Valid PageableDto pageRequest) {
        return tagService.findAll(pageRequest);
    }

    /**
     * Inserts new Tag
     *
     * @return TagDto just inserted
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto insert(@Valid @RequestBody TagDto tagDto) {
        return tagService.insert(tagDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        tagService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/popular")
    public List<TagDto> findMostUsed() {
        return tagService.findMostUsed();
    }
}
