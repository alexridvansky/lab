package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.TagService;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.TagDto;
import com.epam.esm.spring.web.hateoas.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tags")
public class TagController implements Controller<TagDto> {

    private final TagService tagService;
    private final LinkBuilder<TagDto> linkBuilder;

    @Override
    @PreAuthorize("hasRole('ROLE_ROOT') OR hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        TagDto tag = tagService.findById(id);
        linkBuilder.addRemoveLink(tag);
        linkBuilder.addFindAllLink(tag);

        return tag;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ROOT') OR hasRole('ROLE_USER')")
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
    @PreAuthorize("hasRole('ROLE_ROOT')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> insert(@Valid @RequestBody TagDto tagDto) {
        tagService.insert(tagDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ROOT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        tagService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ROOT')")
    @GetMapping("/popular")
    public TagDto findMostUsed() {

        return tagService.findMostUsed();
    }
}
