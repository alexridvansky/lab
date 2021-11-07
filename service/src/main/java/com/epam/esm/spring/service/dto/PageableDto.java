package com.epam.esm.spring.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableDto {

    @PositiveOrZero
    private Integer page;

    @Positive
    private Integer size;
}