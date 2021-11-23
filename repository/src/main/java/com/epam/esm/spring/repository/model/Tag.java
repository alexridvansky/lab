package com.epam.esm.spring.repository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Tag extends AbstractEntity {

    @Column(length = 45, nullable = false, unique = true)
    private String name;
}