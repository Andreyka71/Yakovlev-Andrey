package Homework.Spring;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(classes = Homework1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostgresContainerTest {

    @LocalServerPort
    private int port;

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test1")
            .withUsername("postgres")
            .withPassword("123")
            .withInitScript("init.sql");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        postgres.start();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.enabled", () -> "false");
    }

    @Test
    void containerStartsAndInitializes() {
        assertTrue(postgres.isRunning());
    }
}