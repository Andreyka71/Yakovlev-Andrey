package Homework.Spring.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TelegramToken telegramToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Device> devices;
}