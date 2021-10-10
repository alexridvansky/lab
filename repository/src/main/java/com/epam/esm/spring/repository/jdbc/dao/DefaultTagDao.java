package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.jdbc.mapper.TagRowMapper;
import com.epam.esm.spring.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class DefaultTagDao implements TagDao {
    private static final String SQL_INSERT = "INSERT INTO tag (name) VALUES (?)";
    private static final String SQL_FIND_ALL = "SELECT id AS t_id, name AS t_name FROM tag";
    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = ?";
    private static final String SQL_FIND_BY_NAME = SQL_FIND_ALL + " WHERE name = ?";
    private static final String SQL_ORDER_BY_ID = " ORDER BY id";
    private static final String SQL_COUNT = "SELECT count(*) FROM tag";
    private static final String SQL_COUNT_BY_NAME = SQL_COUNT + " WHERE name = ?";
    private static final String SQL_COUNT_BY_ID = SQL_COUNT + " WHERE id = ?";
    private static final String SQL_COUNT_IN_CROSS_TABLE = "SELECT count(*) FROM certificate_tag_xref WHERE tag_id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM tag WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    @Autowired
    public DefaultTagDao(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public Tag insert(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        tag.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL + SQL_ORDER_BY_ID, tagRowMapper);
    }

    @Override
    public Optional<Tag> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, tagRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> findByName(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, tagRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isExist(String name) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_NAME, Integer.class, name) > 0;
    }

    @Override
    public boolean isExist(long id) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_ID, Integer.class, id) > 0;
    }

    @Override
    public boolean isUsed(long id) {
        return jdbcTemplate.queryForObject(SQL_COUNT_IN_CROSS_TABLE, Integer.class, id) > 0;
    }

    @Override
    public boolean deleteById(long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) == 1;
    }
}
