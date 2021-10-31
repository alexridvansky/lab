package com.epam.esm.spring.repository.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.criteria.CriteriaBuilder;
import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.epam.esm.spring")
@PropertySource("classpath:jdbc.properties")
@Profile("prod")
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
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.epam.esm.spring.repository");
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean().getObject());

        return transactionManager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

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
    public CriteriaBuilder criteriaBuilder() {
        return localContainerEntityManagerFactoryBean().getNativeEntityManagerFactory().getCriteriaBuilder();
    }
}
