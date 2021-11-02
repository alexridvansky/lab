package com.epam.esm.spring.web.hateoas;

import com.epam.esm.spring.service.dto.OrderDto;
import com.epam.esm.spring.web.config.ConfigProperties;
import com.epam.esm.spring.web.controller.OrderController;
import org.springframework.stereotype.Component;

@Component
public class OrderLinkBuilder extends AbstractLinkBuilder<OrderDto> {

    public OrderLinkBuilder(ConfigProperties properties) {
        super(properties);
    }

    @Override
    public OrderDto addRemoveLink(OrderDto orderDto) {
        return addRemoveLinks(orderDto, OrderController.class);
    }

    @Override
    public OrderDto addFindAllLink(OrderDto orderDto) {
        return addFindAllLink(orderDto, OrderController.class);
    }

    @Override
    public OrderDto addFindByIdLink(OrderDto orderDto) {
        return addFindByIdLink(orderDto, OrderController.class);
    }
}
