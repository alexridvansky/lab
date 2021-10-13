package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.jdbc.mapper.CertificateExtractor;
import com.epam.esm.spring.repository.jdbc.querybuilder.QueryBuilder;
import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.spring.repository.jdbc.dao.CertificateSqlQuery.SQL_COUNT_BY_ID;
import static com.epam.esm.spring.repository.jdbc.dao.CertificateSqlQuery.SQL_DELETE_BY_ID;
import static com.epam.esm.spring.repository.jdbc.dao.CertificateSqlQuery.SQL_FIND_ALL;
import static com.epam.esm.spring.repository.jdbc.dao.CertificateSqlQuery.SQL_FIND_BY_ID;
import static com.epam.esm.spring.repository.jdbc.dao.CertificateSqlQuery.SQL_INSERT;
import static com.epam.esm.spring.repository.jdbc.dao.CertificateSqlQuery.SQL_ORDER_BY_ID;
import static com.epam.esm.spring.repository.jdbc.dao.CertificateSqlQuery.SQL_TAG_ATTACH;
import static com.epam.esm.spring.repository.jdbc.dao.CertificateSqlQuery.SQL_TAG_DETACH;

@Repository
public class DefaultCertificateDao implements CertificateDao {

    public static final int EMPTY_RESULT = 0;
    private final CertificateExtractor certificateExtractor;
    private final JdbcTemplate jdbcTemplate;
    private final QueryBuilder queryBuilder;

    @Autowired
    public DefaultCertificateDao(JdbcTemplate jdbcTemplate,
                                 CertificateExtractor certificateExtractor,
                                 QueryBuilder queryBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateExtractor = certificateExtractor;
        this.queryBuilder = queryBuilder;
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL + SQL_ORDER_BY_ID, certificateExtractor);
    }

    @Override
    public List<Certificate> findBy(Map<String, String> params) {
        params.put("query", SQL_FIND_ALL);
        String sqlQuery = queryBuilder.buildQueryForSearch(params);
        return jdbcTemplate.query(sqlQuery, certificateExtractor);
    }

    @Override
    public Optional<Certificate> findById(long id) {
        try {
            return Optional.of(jdbcTemplate.query(SQL_FIND_BY_ID, certificateExtractor, id).stream().findAny().get());
        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public Certificate insert(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setBigDecimal(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            return ps;
        }, keyHolder);

        certificate.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return certificate;
    }

    @Override
    public boolean update(Long id, Map<String, Object> data) {
        String updateQuery = queryBuilder.buildQueryForUpdate(data);
        List<Object> value = new ArrayList<>(data.values());
        value.add(id);
        jdbcTemplate.update(updateQuery, value.toArray());

        return true;
    }

    @Override
    public boolean isExist(long id) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_ID, Integer.class, id) > EMPTY_RESULT;
    }

    @Override
    public boolean insertTagIntoXrefTable(List<Tag> tags, long id) {
        List<Object[]> batch = tags.stream()
                .map(tag -> new Object[]{id, tag.getId()})
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(SQL_TAG_ATTACH, batch);

        return true;
    }

    @Override
    public boolean detachTagFromXrefTable(long id) {
        return jdbcTemplate.update(SQL_TAG_DETACH, id) > EMPTY_RESULT;
    }

    @Override
    public boolean deleteById(long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) > EMPTY_RESULT;
    }
}
