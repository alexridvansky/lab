package com.epam.esm.spring.repository.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm.spring")
@PropertySource("classpath:jdbc.properties")
public class RepositoryConfig {
    @Value("${db.driver}")
    private String driverClassName;
    @Value("${db.url}")
    private String dbUrl;
    @Value("${user}")
    private String dbUsername;
    @Value("${password}")
    private String dbPassword;
    @Value("${pool.size}")
    private int poolSize;

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driverClassName);
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(dbUsername);
        basicDataSource.setPassword(dbPassword);
        basicDataSource.setInitialSize(poolSize);

        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
