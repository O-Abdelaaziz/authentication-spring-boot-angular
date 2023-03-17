package com.auth.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
@Table(value = "users",schema = "public")
public class User {

    @Id
    @Column("id")
    private Long id;

    @NotBlank(message = "First name can't be empty")
    @Column("first_name")
    private String firstName;

    @NotBlank(message = "Last name can't be empty")
    @Column("last_name")
    private String lastName;

    @NotBlank(message = "email can't be empty")
    @Email(message = "email not valid")
    @Column("email")
    private String email;

    @NotBlank(message = "password can't be empty")
    @Column("password")
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
