package com.epam.esm.spring.repository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this.id == null) {
            return false;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof AbstractEntity)) {
            return false;
        }

        AbstractEntity a = (AbstractEntity) o;

        if (a.id == null) {
            return false;
        }

        return this.id.equals(a.id);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
