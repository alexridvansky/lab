package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.OrderService;
import com.epam.esm.spring.service.dto.OrderDto;
import com.epam.esm.spring.service.dto.OrderInsertDto;
import com.epam.esm.spring.web.config.ConfigProperties;
import com.epam.esm.spring.web.hateoas.LinkBuilder;
import com.epam.esm.spring.web.hateoas.OrderLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController implements Controller<OrderDto> {

    private final OrderService orderService;
    private final LinkBuilder<OrderDto> linkBuilder;
    private final ConfigProperties properties;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderLinkBuilder linkBuilder,
                           ConfigProperties properties) {
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
        this.properties = properties;
    }

    @Override
    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable Long id) {
        OrderDto orderDto = orderService.findById(id);
        linkBuilder.addFindAllLink(orderDto);
        linkBuilder.addRemoveLink(orderDto);

        return orderDto;
    }

    @Override
    @GetMapping
    public List<OrderDto> findAll(@RequestParam(name = "page", required = false) Integer page,
                                  @RequestParam(name = "size", required = false) Integer size) {
        if (page == null) { page = properties.getOffsetDefault(); }
        if (size == null) { size = properties.getLimitDefault(); }

        return orderService.findAll().stream()
                .map(linkBuilder::addFindByIdLink)
                .map(linkBuilder::addRemoveLink)
                .collect(Collectors.toList());
    }

    /**
     * Inserts new Order
     *
     * @return OrderDto just inserted
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto insert(@Valid @RequestBody OrderInsertDto orderDto) {
        return orderService.insert(orderDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        orderService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
