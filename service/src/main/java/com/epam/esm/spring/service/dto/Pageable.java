package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;


@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Pageable {

    private static Integer DEFAULT_PAGE = 0;
    private static Integer DEFAULT_PAGE_SIZE = 10;

    @Min(0)
    private Integer page = DEFAULT_PAGE;

    @Min(1)
    private Integer size = DEFAULT_PAGE_SIZE;
}