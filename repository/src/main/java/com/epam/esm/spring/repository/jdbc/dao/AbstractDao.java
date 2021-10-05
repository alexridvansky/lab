package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao <T extends AbstractEntity> {
    abstract List<T> findAll();
    abstract Optional<T> findById(long id);
    abstract T insert(T t);
    abstract boolean deleteById(long id);
}
