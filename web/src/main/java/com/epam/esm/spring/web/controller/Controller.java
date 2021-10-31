package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.dto.AbstractDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Controller provides service within entities.
 */
@Validated
public interface Controller<K extends AbstractDto> {

    /**
     * Gets entity by its ID
     *
     * @return EntityDto
     */
    K findById(@PathVariable @Positive Long id);

    /**
     * Gets list of entities available
     *
     * @return List<EntityDto> the list of entities
     */
    List<K> findAll();

    /**
     * Inserts new Entity
     *
     * @return EntityDto just inserted
     */
    K insert(@Valid @RequestBody K k);

    /**
     * Removes Entity by its ID
     *
     * @param id the id of Entity to remove
     */
    ResponseEntity<Void> remove(@PathVariable @Positive Long id);
}