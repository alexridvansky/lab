package com.epam.esm.spring.repository.jdbc.mapper;

import com.epam.esm.spring.repository.model.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.CERTIFICATE_CREATE_DATE;
import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.CERTIFICATE_DESCRIPTION;
import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.CERTIFICATE_DURATION;
import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.CERTIFICATE_ID;
import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.CERTIFICATE_LAST_UPDATE_DATE;
import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.CERTIFICATE_NAME;
import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.CERTIFICATE_PRICE;

@Component
public class CertificateRowMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Certificate.builder()
                .id(rs.getLong(CERTIFICATE_ID))
                .name(rs.getString(CERTIFICATE_NAME))
                .description(rs.getString(CERTIFICATE_DESCRIPTION))
                .price(rs.getBigDecimal(CERTIFICATE_PRICE))
                .duration(rs.getInt(CERTIFICATE_DURATION))
                .createDate(rs.getTimestamp(CERTIFICATE_CREATE_DATE).toLocalDateTime())
                .lastUpdateDate(rs.getTimestamp(CERTIFICATE_LAST_UPDATE_DATE).toLocalDateTime())
                .build();
    }
}
