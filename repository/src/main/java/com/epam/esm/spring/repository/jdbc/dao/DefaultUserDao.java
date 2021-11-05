package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.model.PageParam;
import com.epam.esm.spring.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.spring.repository.jdbc.querybuilder.QueryDictionary.USERNAME;

@Repository
public class DefaultUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(PageParam pageParam) {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(pageParam.getPage() * pageParam.getSize())
                .setMaxResults(pageParam.getSize())
                .getResultList();
    }

    @Override
    public Long countEntry() {
        return entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username LIKE :username", User.class)
                .setParameter(USERNAME, username)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
}
