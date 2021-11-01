package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.dto.AbstractDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
//    @RequestMapping(params = { "page", "size" })
    List<K> findAll(@Positive @RequestParam(name = "page", required = false) Integer page,
                    @Positive @RequestParam(name = "size", required = false) Integer size);

    /**
     * Removes Entity by its ID
     *
     * @param id the id of Entity to remove
     */
    ResponseEntity<Void> remove(@PathVariable @Positive Long id);
}