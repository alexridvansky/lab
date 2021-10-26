package com.epam.esm.spring.repository.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
@EntityScan(basePackages = "com.epam.esm.spring.repository")
@EnableJpaRepositories(basePackages = "com.epam.esm.spring.repository")
public class TestConfigJpa {
    public static void main(String[] args) {
        SpringApplication.run(TestConfigJpa.class, args);
    }

    private static final String SQL_TABLES = "classpath:tables.sql";
    private static final String SQL_FILL_UP = "classpath:db_fillup_data.sql";

    @Bean
    public EmbeddedDatabase embeddedDatabase() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();

        return databaseBuilder
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript(SQL_TABLES)
                .addScript(SQL_FILL_UP)
                .build();
    }


}
