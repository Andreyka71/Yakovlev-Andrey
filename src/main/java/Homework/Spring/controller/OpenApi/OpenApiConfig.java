package Homework.Spring.controller.OpenApi;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi UserApi() {
        return GroupedOpenApi.builder()
            .group("user-management")
            .pathsToMatch("/user/**")
            .build();
    }

    @Bean
    public GroupedOpenApi DeviceApi() {
        return GroupedOpenApi.builder()
                             .group("Devices")
                             .pathsToMatch("/**/device/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi RuleApi() {
        return GroupedOpenApi.builder()
                             .group("Rules")
                             .pathsToMatch("/**/rule/**")
                             .build();
    }
}
