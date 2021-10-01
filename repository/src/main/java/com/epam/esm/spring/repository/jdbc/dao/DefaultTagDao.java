package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.jdbc.mapper.TagRowMapper;
import com.epam.esm.spring.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class DefaultTagDao extends AbstractDao<Tag> implements TagDao {
    private static final String SQL_INSERT = "INSERT INTO tag (name) VALUES (?)";
    private static final String SQL_FIND_ALL = "SELECT id, name FROM tag";
    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultTagDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag insert(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue()).orElseThrow(() ->
                new DataAccessException("Just stored entry can't be read back") {
                });
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new TagRowMapper());
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new TagRowMapper(), id));
    }

}
