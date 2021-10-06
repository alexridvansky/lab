package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends AbstractEntity> {
    List<T> findAll();
    Optional<T> findById(long id);
    T insert(T t);
    boolean deleteById(long id);
}
