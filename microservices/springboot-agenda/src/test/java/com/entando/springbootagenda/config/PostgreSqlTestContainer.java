package com.entando.springbootagenda.config;

import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public abstract class PostgreSqlTestContainer {

    private static final Logger log = LoggerFactory.getLogger(PostgreSqlTestContainer.class);

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = initPostgres();

    private static PostgreSQLContainer<?> initPostgres() {
        DockerImageName entandoPostgres = DockerImageName.parse("postgres:14.5");

        return new PostgreSQLContainer<>(entandoPostgres)
                    .withDatabaseName("agenda-db")
                    .withTmpFs(Collections.singletonMap("/testtmpfs", "rw"))
                    .withLogConsumer(new Slf4jLogConsumer(log))
                    .withReuse(true);
    }

    @DynamicPropertySource
    static void registerPostgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}

