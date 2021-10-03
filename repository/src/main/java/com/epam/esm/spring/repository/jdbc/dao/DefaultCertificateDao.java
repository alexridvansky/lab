package com.epam.esm.spring.repository.jdbc.dao;

import com.epam.esm.spring.repository.jdbc.mapper.CertificateRowMapper;
import com.epam.esm.spring.repository.model.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DefaultCertificateDao extends AbstractDao<Certificate> implements CertificateDao {
    private static final String SQL_ORDER_BY_ID = "ORDER BY id";
    private static final String SQL_FIND_ALL =
            "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.id AS t_id, t.name AS t_name " +
                    "FROM gift_certificate AS gc " +
                    "INNER JOIN certificate_tag_xref AS ctx on gc.id = ctx.certificate_id " +
                    "INNER JOIN tag t on ctx.tag_id = t.id ";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultCertificateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL + SQL_ORDER_BY_ID, new CertificateRowMapper());
    }

    @Override
    public Optional<Certificate> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Certificate insert(Certificate certificate) {
        return null;
    }
}
