package com.epam.esm.spring.repository.jdbc.mapper;

import com.epam.esm.spring.repository.model.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.*;

public class CertificateRowMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Certificate.builder()
                .id(rs.getLong(C_ID))
                .name(rs.getString(C_NAME))
                .description(rs.getString(C_DESCRIPTION))
                .price(rs.getBigDecimal(C_PRICE))
                .duration(rs.getInt(C_DURATION))
                .createDate(rs.getTimestamp(C_CREATE_DATE).toLocalDateTime())
                .lastUpdateDate(rs.getTimestamp(C_LAST_UPDATE_DATE).toLocalDateTime())
                .build();
    }
}
