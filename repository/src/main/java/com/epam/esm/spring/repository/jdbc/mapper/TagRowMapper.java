package com.epam.esm.spring.repository.jdbc.mapper;

import com.epam.esm.spring.repository.model.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.T_ID;
import static com.epam.esm.spring.repository.jdbc.mapper.ColumnName.T_NAME;

@Component
public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Tag.builder()
                .id(rs.getLong(T_ID))
                .name(rs.getString(T_NAME))
                .build();
    }
}
