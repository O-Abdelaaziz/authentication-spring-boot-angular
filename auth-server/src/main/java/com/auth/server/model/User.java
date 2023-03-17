package com.auth.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

/**
 * @Created 17/03/2023 - 08:58
 * @Package com.auth.server.model
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Getter
@Setter
@ToString
public class User {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static User of(String firstName, String lastName, String email, String password) {
        return new User(null, firstName, lastName, email, password);
    }

    @PersistenceCreator
    private User(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
