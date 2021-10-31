package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.dto.OrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class OrderController implements Controller<OrderDto> {



    @Override
    public OrderDto findById(Long id) {
        return null;
    }

    @Override
    public List<OrderDto> findAll() {
        return null;
    }

    @Override
    public OrderDto insert(OrderDto orderDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> remove(Long id) {
        return null;
    }
}
