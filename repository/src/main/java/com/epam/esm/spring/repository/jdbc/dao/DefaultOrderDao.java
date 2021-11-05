package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.Order;
import com.epam.esm.spring.repository.model.PageParam;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.ID;

@Repository
public class DefaultOrderDao implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> findAll(PageParam pageParam) {
        return entityManager.createQuery("SELECT o FROM Order o", Order.class)
                .setFirstResult(pageParam.getPage() * pageParam.getSize())
                .setMaxResults(pageParam.getSize())
                .getResultList();
    }

    @Override
    public Long countEntry() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Order o", Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id = :id", Order.class)
                .setParameter(ID, userId)
                .getResultList();
    }

    @Override
    public Order insert(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }
}
