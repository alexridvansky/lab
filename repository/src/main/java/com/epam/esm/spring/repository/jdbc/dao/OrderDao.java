package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Order;

import java.util.List;

public interface OrderDao extends CreateDeleteDao<Order> {
    /**
     * Gets all purchases of user by id.
     *
     * @param userId id of the User
     * @return List of certificates
     */
    List<Order> findByUserId(Long userId);
}
