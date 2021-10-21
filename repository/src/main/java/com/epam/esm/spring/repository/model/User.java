package com.epam.esm.spring.repository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class User extends AbstractEntity{

    @Column(length = 45, nullable = false, unique = true)
    private String username;

    @Column(length = 45, nullable = false)
    private String password;

    @Column(length = 45)
    private String firstname;

    @Column(length = 45)
    private String lastname;
}
