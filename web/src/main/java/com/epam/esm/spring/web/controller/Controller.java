package com.epam.esm.spring.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Positive;

@Validated
public interface Controller<K> {
    ResponseEntity<Void> remove(@PathVariable @Positive Long id);
}
