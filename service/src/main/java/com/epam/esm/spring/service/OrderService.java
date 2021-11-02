package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.OrderDto;
import com.epam.esm.spring.service.dto.OrderInsertDto;

import java.util.List;

public interface OrderService extends CrudService<OrderDto> {

    /**
     * Adds new entry to the db.
     *
     * @param orderInsertDto entity object to be added to
     * @return T.class object just added to the db
     */
    OrderDto insert(OrderInsertDto orderInsertDto);

    /**
     * Gets all purchases of user by id.
     *
     * @param userId id of the User
     * @return List of certificates
     */
    List<OrderDto> findByUserId(Long userId);
}
