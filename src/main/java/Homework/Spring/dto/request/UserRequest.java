package Homework.Spring.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private Long id;
    private String login;
    private String password;
    private String telegramToken;
}
