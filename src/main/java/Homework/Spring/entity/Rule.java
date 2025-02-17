package Homework.Spring.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rule {
    private Long id;
    private String rule;
    private Device device;
}
