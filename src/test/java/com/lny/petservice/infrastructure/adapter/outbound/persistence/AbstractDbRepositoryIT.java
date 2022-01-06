package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = AbstractDbRepositoryIT.DockerPostgresDataSourceInitializer.class)
@Testcontainers
abstract class AbstractDbRepositoryIT {

    public static PostgreSQLContainer<?> postgresDBContainer =
            new PostgreSQLContainer<>("postgres:11")
                    .withDatabaseName("petdb")
                    .withUsername("tester")
                    .withPassword("test1234");

    static {
        postgresDBContainer.start();
    }

    public static class DockerPostgresDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    // write
                    "datasource.write.jdbc-url=" + postgresDBContainer.getJdbcUrl(),
                    "datasource.write.username=" + postgresDBContainer.getUsername(),
                    "datasource.write.password=" + postgresDBContainer.getPassword(),
                    // read
                    "datasource.read.jdbc-url=" + postgresDBContainer.getJdbcUrl(),
                    "datasource.read.username=" + postgresDBContainer.getUsername(),
                    "datasource.read.password=" + postgresDBContainer.getPassword(),
                    // liquibase
                    "spring.liquibase.url=" + postgresDBContainer.getJdbcUrl(),
                    "spring.liquibase.user=" + postgresDBContainer.getUsername(),
                    "spring.liquibase.password=" + postgresDBContainer.getPassword()
            );
        }
    }

}