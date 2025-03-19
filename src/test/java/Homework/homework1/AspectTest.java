package Homework.homework1;

import Homework.Spring.Homework1Application;
import Homework.Spring.aop.LoggingAspect;
import Homework.Spring.dto.request.UserRequest;
import Homework.Spring.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(classes = Homework1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AspectTest {

    @LocalServerPort
    private int port;

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("123")
            .withInitScript("init.sql");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        postgres.start();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LoggingAspect loggingAspect;

    @Test
    public void testUserController() {
        assertEquals(0, loggingAspect.getCounter());

        UserRequest userRequest = new UserRequest();
        userRequest.setLogin("testuser");
        userRequest.setPassword("123");

        ResponseEntity<UserResponse> createResponse =
                restTemplate.postForEntity("/user/registration", userRequest, UserResponse.class);

        UserResponse userResponse = createResponse.getBody();
        assertNotNull(userResponse);

        assertEquals(1, loggingAspect.getCounter());
    }
}