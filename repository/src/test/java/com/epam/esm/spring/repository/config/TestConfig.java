//package com.epam.esm.spring.repository.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@ActiveProfiles("jdbcTest")
//@ComponentScan("com.epam.esm.spring")
//public class TestConfig {
//
//    private static final String SQL_TABLE_GC = "classpath:tables.sql";
//    private static final String SQL_TABLE_T = "classpath:test_tag.sql";
//    private static final String SQL_TABLE_XREF = "classpath:test_certificate_tag_xref.sql";
//    private static final String SQL_FILL_UP = "classpath:db_fillup_data.sql";
//
//    @Bean
//    public EmbeddedDatabase embeddedDatabase() {
//        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
//
//        return databaseBuilder
//                .generateUniqueName(true)
//                .setType(EmbeddedDatabaseType.H2)
//                .setScriptEncoding("UTF-8")
//                .ignoreFailedDrops(true)
//                .addScript(SQL_TABLE_T)
//                .addScript(SQL_TABLE_GC)
//                .addScript(SQL_TABLE_XREF)
//                .addScript(SQL_FILL_UP)
//                .build();
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(EmbeddedDatabase embeddedDatabase) {
//        return new JdbcTemplate(embeddedDatabase);
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EmbeddedDatabase embeddedDatabase) {
//        return new DataSourceTransactionManager(embeddedDatabase);
//    }
//}
