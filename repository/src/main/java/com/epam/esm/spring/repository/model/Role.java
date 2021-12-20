package com.epam.esm.spring.repository.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity {

    @Column(name = "name", length = 45, nullable = false, unique = true)
    private String role;

    public Role(long roleId, String role) {
        super(roleId);
        this.role = role;
    }
}