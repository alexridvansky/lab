package com.epam.esm.spring.service.dto;

import java.util.Collection;

public class Page<T> {
    private Collection<T> content;
    private Integer size;
    private Integer page;
    private Long totalPages;
    private Long totalEntries;

    public Page(Collection<T> content, Pageable pageRequest, Long totalEntries) {
        this.content = content;
        this.size = pageRequest.getSize();
        this.page = pageRequest.getPage();
        this.totalPages = totalEntries / size + 1;
    }
}