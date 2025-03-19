package Homework.Spring.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramToken {
    private String token;
    private Long userId;
}
