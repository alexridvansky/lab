package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
        this.totalEntries = totalEntries;
        this.totalPages = totalEntries / size + 1;
    }
}