package com.epam.esm.spring.web.controller;

import com.epam.esm.spring.service.OrderService;
import com.epam.esm.spring.service.dto.OrderDto;
import com.epam.esm.spring.service.dto.OrderInsertDto;
import com.epam.esm.spring.service.dto.Page;
import com.epam.esm.spring.service.dto.PageableDto;
import com.epam.esm.spring.service.dto.UserDetailsDto;
import com.epam.esm.spring.web.exception.CustomAccessDeniedException;
import com.epam.esm.spring.web.hateoas.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
public class OrderController implements Controller<OrderDto> {

    private static final String ACCESS_DENIED_ERROR_MESSAGE = "error.access_denied";
    private final OrderService orderService;
    private final LinkBuilder<OrderDto> linkBuilder;

    @Override
    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable Long id) {
        OrderDto orderDto = orderService.findById(id);
        linkBuilder.addFindAllLink(orderDto);
        linkBuilder.addRemoveLink(orderDto);
        return orderDto;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Page<OrderDto> findAll(@Valid PageableDto pageRequest) {
        return orderService.findAll(pageRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @userSecurity.hasUserId(authentication, #id)")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.id")
    @GetMapping("/user/{id}")
    public List<OrderDto> findByUserId(@PathVariable @Positive Long id) {
        return orderService.findByUserId(id);
    }

    /**
     * Inserts new Order
     *
     * @return OrderDto just inserted
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody OrderInsertDto orderDto, Authentication authentication) {
        Long userId = extractUserIdFromAuthentication(authentication);
        orderDto.setUserId(userId);

        orderService.insert(orderDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Long extractUserIdFromAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetailsDto)) {
            throw new CustomAccessDeniedException(ACCESS_DENIED_ERROR_MESSAGE);
        }

        UserDetailsDto user = (UserDetailsDto) principal;

        return user.getId();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        orderService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
