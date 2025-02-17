package Homework.Spring.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
    private Long id;
    private String login;
    private String password;
    private TelegramToken telegramToken;
    private List<Device> devices;
}