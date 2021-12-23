package com.lny.petservice.config;

import java.util.Properties;

public class DataSourceCommon {

    private DataSourceCommon() {
        throw new IllegalStateException("Utility class");
    }

    public static final String MODEL_PACKAGE = "com.lny.petservice.domain.model";

    public static Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.ddl-auto", "validate");
        properties.put("hibernate.default_schema", "public");
        properties.put("show-sql", "true");
        return properties;
    }
}
