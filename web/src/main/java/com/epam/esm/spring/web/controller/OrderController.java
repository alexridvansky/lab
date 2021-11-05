package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.OrderService;
import com.epam.esm.spring.service.dto.OrderDto;
import com.epam.esm.spring.service.dto.OrderInsertDto;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.Pageable;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController implements Controller<OrderDto> {

    private final OrderService orderService;
    private final LinkBuilder<OrderDto> linkBuilder;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderLinkBuilder linkBuilder) {
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
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
    public ResponseEntity<Page<OrderDto>> findAll(@Valid Pageable pageRequest) {
        return new ResponseEntity<>(orderService.findAll(pageRequest), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public List<OrderDto> findByUserId(@PathVariable @Positive Long id) {
        return orderService.findByUserId(id);
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
