package com.epam.esm.spring.repository.jdbc.mapper;

import com.epam.esm.spring.repository.model.Certificate;
import com.epam.esm.spring.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.C_ID;

@Component
public class CertificateExtractor implements ResultSetExtractor<List<Certificate>> {
    private final TagRowMapper tagRowMapper;
    private final CertificateRowMapper certificateRowMapper;

    @Autowired
    public CertificateExtractor(TagRowMapper tagRowMapper, CertificateRowMapper certificateRowMapper) {
        this.tagRowMapper = tagRowMapper;
        this.certificateRowMapper = certificateRowMapper;
    }

    @Override
    public List<Certificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Certificate> certificates = new LinkedHashMap<>();
        while (rs.next()) {
            Long id = rs.getLong(C_ID);
            Certificate certificate = certificates.get(id);
            if (certificate == null) {
                certificate = certificateRowMapper.mapRow(rs, 1);
            }

            Tag tag = tagRowMapper.mapRow(rs, 8);
            assert certificate != null;
            certificate.getTags().add(tag);
            certificates.put(certificate.getId(), certificate);
        }
            return new ArrayList<>(certificates.values());
    }
}
