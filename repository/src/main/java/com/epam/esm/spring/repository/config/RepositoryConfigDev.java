package com.epam.esm.spring.repository.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan("com.epam.esm.spring")
@Profile("dev")
public class RepositoryConfigDev {
    private static final String SQL_TABLE_GIFT_CERTIFICATE = "classpath:test_gift_certificate.sql";
    private static final String SQL_TABLE_TAG = "classpath:test_tag.sql";
    private static final String SQL_TABLE_XREF = "classpath:test_certificate_tag_xref.sql";
    private static final String SQL_DATA_FOR_FILL_UP = "classpath:db_fillup.sql";

    @Bean
    public EmbeddedDatabase embeddedDatabase() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();

        return databaseBuilder
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript(SQL_TABLE_TAG)
                .addScript(SQL_TABLE_GIFT_CERTIFICATE)
                .addScript(SQL_TABLE_XREF)
                .addScript(SQL_DATA_FOR_FILL_UP)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(EmbeddedDatabase embeddedDatabase) {
        return new JdbcTemplate(embeddedDatabase);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EmbeddedDatabase embeddedDatabase) {
        return new DataSourceTransactionManager(embeddedDatabase);
    }
}
