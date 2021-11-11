package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private Collection<T> content;
    private Integer size;
    private Integer page;
    private Long totalPages;
    private Long totalEntries;

    public Page(Collection<T> content, PageableDto pageRequest, Long totalEntries) {
        this.content = content;
        this.size = pageRequest.getSize();
        this.page = pageRequest.getPage();
        this.totalEntries = totalEntries;
        this.totalPages = totalEntries / size + 1;
    }
}