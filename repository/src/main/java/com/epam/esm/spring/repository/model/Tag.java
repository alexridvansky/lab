package com.epam.esm.spring.repository.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Tag extends AbstractEntity {
    private String name;
}
