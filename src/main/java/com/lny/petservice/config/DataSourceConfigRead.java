package com.lny.petservice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

import static com.lny.petservice.config.DataSourceCommon.MODEL_PACKAGE;

@Configuration
@ConfigurationProperties("datasource.read")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryRead",
        transactionManagerRef = "transactionManagerRead",
        basePackages = {"com.lny.petservice.infrastructure.adapter.read"}
)
public class DataSourceConfigRead extends HikariConfig {

    public static final String PERSISTENCE_UNIT_NAME = "read";

    @Bean
    public HikariDataSource dataSourceRead() {
        return new HikariDataSource(this);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRead(
            final HikariDataSource dataSourceRead) {

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSourceRead);
        bean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        bean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        bean.setPackagesToScan(MODEL_PACKAGE);
        bean.setJpaProperties(DataSourceCommon.getJpaProperties());

        return bean;

    }

    @Bean
    public PlatformTransactionManager transactionManagerRead(EntityManagerFactory entityManagerFactoryRead) {
        return new JpaTransactionManager(entityManagerFactoryRead);
    }
}
