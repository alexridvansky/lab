package com.epam.esm.spring.service;

import com.epam.esm.spring.service.dto.OrderDto;
import com.epam.esm.spring.service.dto.OrderInsertDto;

public interface OrderService extends CrudService<OrderDto> {
    /**
     * Adds new entry to the db
     *
     * @param t entity object to be added to
     * @return T.class object just added to the db
     */
    OrderDto insert(OrderInsertDto t);
}
