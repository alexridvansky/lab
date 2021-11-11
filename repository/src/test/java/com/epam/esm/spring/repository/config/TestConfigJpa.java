package com.epam.esm.spring.repository.config;

import com.epam.esm.spring.repository.jdbc.dao.DefaultCertificateDao;
import com.epam.esm.spring.repository.jdbc.dao.DefaultTagDao;
import com.epam.esm.spring.repository.jdbc.querybuilder.QueryBuilder;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.criteria.CriteriaBuilder;

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

    @Bean
    public DefaultTagDao defaultTagDao() {
        return new DefaultTagDao();
    }

    @Bean
    public CriteriaBuilder criteriaBuilder() {
        return localContainerEntityManagerFactoryBean().getNativeEntityManagerFactory().getCriteriaBuilder();
    }

    @Bean
    public DefaultCertificateDao defaultCertificateDao() {
        return new DefaultCertificateDao(new QueryBuilder());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(embeddedDatabase());
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
}
