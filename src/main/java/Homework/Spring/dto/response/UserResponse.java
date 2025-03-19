package Homework.Spring.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String login;
    private String password;
    private String telegramToken;
    private List<DeviceResponse> devices;
}
