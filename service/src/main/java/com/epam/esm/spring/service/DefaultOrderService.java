package com.epam.esm.spring.service;

import com.epam.esm.spring.repository.jdbc.dao.CertificateDao;
import com.epam.esm.spring.repository.jdbc.dao.OrderDao;
import com.epam.esm.spring.repository.jdbc.dao.UserDao;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.Order;
import com.epam.esm.spring.repository.model.User;
import com.epam.esm.spring.service.dto.OrderDto;
import com.epam.esm.spring.service.dto.OrderInsertDto;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.exception.SubEntryNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_CERTIFICATE_NOT_FOUND;
import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_ORDER_NOT_FOUND;
import static com.epam.esm.spring.service.exception.ErrorMessage.ERROR_USER_NOT_FOUND;
import static com.epam.esm.spring.service.exception.ErrorMessage.ID;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final CertificateDao certificateDao;
    private final ModelMapper modelMapper;

    @Autowired
    public DefaultOrderService(OrderDao orderDao,
                               UserDao userDao,
                               CertificateDao certificateDao,
                               ModelMapper modelMapper) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OrderDto> findAll() {
        return orderDao.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long id) {
        return orderDao.findById(id)
                .map(order -> modelMapper.map(order, OrderDto.class))
                .orElseThrow(() -> new SubEntryNotFoundException(ERROR_ORDER_NOT_FOUND, ID + id));
    }

    @Override
    public List<OrderDto> findByUserId(Long userId) {
        return orderDao.findByUserId(userId).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderDto insert(OrderInsertDto orderDto) {
        User user = userDao.findById(orderDto.getUserId())
                .orElseThrow(() -> new SubEntryNotFoundException(ERROR_USER_NOT_FOUND,
                        ID + orderDto.getUserId().toString()));

        List<Certificate> certificates = orderDto.getCertificates().stream()
                .map(certificateDto -> certificateDao.findById(certificateDto.getId())
                        .orElseThrow(() -> new EntryNotFoundException(ERROR_CERTIFICATE_NOT_FOUND,
                                ID + certificateDto.getId().toString())))
                .collect(Collectors.toList());

        BigDecimal total = certificates.stream()
                .map(Certificate::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .total(total)
                .user(user)
                .certificates(certificates)
                .build();

        return modelMapper.map(orderDao.insert(order), OrderDto.class);
    }

    @Override
    public OrderDto insert(OrderDto orderDto) {
        return modelMapper.map(orderDao.insert(modelMapper.map(orderDto, Order.class)), OrderDto.class);
    }

    @Transactional
    @Override
    public OrderDto deleteById(Long id) {
        Order order = orderDao.findById(id)
                .orElseThrow(() -> new EntryNotFoundException(ERROR_ORDER_NOT_FOUND, ID + id));

        orderDao.delete(order);

        return modelMapper.map(order, OrderDto.class);
    }
}