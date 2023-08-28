package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "telegram_id", unique = true)
    private Long telegramId;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "balance")
    @org.hibernate.annotations.ColumnDefault("0")
    private Long balance = 0L;

    @Column(name = "roles")
    @org.hibernate.annotations.ColumnDefault("1")
    private Integer roles = 1;

    public UserEntity(String username, Long telegramId) {
        this.username = username;
        this.telegramId = telegramId;
    }
}
