package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.jdbc.mapper.CertificateExtractor;
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
import java.util.*;

@Repository
public class DefaultCertificateDao extends AbstractDao<Certificate> implements CertificateDao {
    private static final String SQL_ORDER_BY_ID = "ORDER BY id";
    private static final String SQL_FIND_ALL =
            "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, " +
                    "t.id AS t_id, t.name AS t_name " +
                    "FROM gift_certificate AS gc " +
                    "LEFT JOIN certificate_tag_xref AS ctx on gc.id = ctx.certificate_id " +
                    "LEFT JOIN tag t on ctx.tag_id = t.id ";
    private final String SQL_FIND_BY_ID = SQL_FIND_ALL + "WHERE gc.id = ?" ;
    private static final String SQL_INSERT = "INSERT INTO gift_certificate (name, description, price, duration) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_TAG_ATTACH = "INSERT INTO certificate_tag_xref (certificate_id, tag_id) " +
            "VALUES (?, ?)";
    private static final String SQL_TAG_DETACH = "DELETE FROM certificate_tag_xref WHERE certificate_id = ?" ;
    private static final String SQL_DELETE_BY_ID = "DELETE FROM gift_certificate WHERE id = ?" ;
    private final CertificateExtractor certificateExtractor;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultCertificateDao(JdbcTemplate jdbcTemplate, CertificateExtractor certificateExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateExtractor = certificateExtractor;
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL + SQL_ORDER_BY_ID, certificateExtractor);
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
    public boolean exists(long id) {
        return false;
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public boolean insertTagIntoXrefTable(List<Tag> tags, long id) {
        List<Object[]> batch = new ArrayList<>();

        for (Tag tag : tags) {
            Object[] values = new Object[]{
                    id,
                    tag.getId()};
            batch.add(values);
        }

        jdbcTemplate.batchUpdate(SQL_TAG_ATTACH, batch);

        return true;
    }

    @Override
    public boolean detachTagFromXrefTable(long id) {
        return jdbcTemplate.update(SQL_TAG_DETACH, id) > 0;
    }

    @Override
    public boolean deleteById(long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) > 0;
    }
}
