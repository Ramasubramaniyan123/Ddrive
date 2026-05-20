package com.learning.spring.orm;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.learning.spring.orm")
@EnableTransactionManagement // Enables @Transactional support
public class AppConfig {

    // 1. DataSource
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springpractice");
        dataSource.setUsername("root");
        dataSource.setPassword("Ram@2005");
        return dataSource;
    }

    // 2. EntityManagerFactory (The JPA Container)
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());

        // Scan for @Entity classes here
        em.setPackagesToScan("com.learning.spring.orm");

        // Use Hibernate as the implementation
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // This forces Spring to proxy the bean as a standard JPA Factory, avoiding the conflict.
        em.setEntityManagerFactoryInterface(jakarta.persistence.EntityManagerFactory.class);

        // Hibernate specific properties
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create"); // create - Auto-create table,  create-drop - Drops the schema again when the SessionFactory & create table, update - update table, validate - thrpws an exception if mismatch
        //Change the dialect to suit your DB
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.show_sql", "true"); // Log SQL for debugging
        em.setJpaProperties(properties);

        return em;
    }

    // 3. Transaction Manager
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
