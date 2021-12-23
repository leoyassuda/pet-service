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
@ConfigurationProperties("datasource.write")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryWrite",
        transactionManagerRef = "transactionManagerWrite",
        basePackages = {"com.lny.petservice.infrastructure.adapter.write"}
)
public class DataSourceConfigWrite extends HikariConfig {

    public static final String PERSISTENCE_UNIT_NAME = "write";

    @Bean
    public HikariDataSource dataSourceWrite() {
        return new HikariDataSource(this);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryWrite(
            final HikariDataSource dataSourceWrite) {

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSourceWrite);
        bean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        bean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        bean.setPackagesToScan(MODEL_PACKAGE);
        bean.setJpaProperties(DataSourceCommon.getJpaProperties());
        return bean;
    }

    @Bean
    public PlatformTransactionManager transactionManagerWrite(EntityManagerFactory entityManagerFactoryWrite) {
        return new JpaTransactionManager(entityManagerFactoryWrite);
    }

}
