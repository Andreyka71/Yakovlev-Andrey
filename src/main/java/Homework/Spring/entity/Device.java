package Homework.Spring.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Device {
    private Long id;
    private String deviceName;
    private String type;
    private User user;
    private List<Rule> rules;
}
