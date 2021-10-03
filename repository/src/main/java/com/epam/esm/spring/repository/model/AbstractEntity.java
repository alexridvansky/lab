package com.epam.esm.spring.repository.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class AbstractEntity {
    private Long id;
}
