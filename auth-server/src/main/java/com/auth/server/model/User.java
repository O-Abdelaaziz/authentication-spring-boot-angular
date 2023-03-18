package com.auth.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @Created 17/03/2023 - 08:58
 * @Package com.auth.server.model
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/


@ToString
@Table(value = "users", schema = "public")
public class User {

    @Getter
    @Id
    @Column("id")
    private Long id;

    @Getter @Setter
    @NotBlank(message = "First name can't be empty")
    @Column("first_name")
    private String firstName;

    @Getter @Setter
    @NotBlank(message = "Last name can't be empty")
    @Column("last_name")
    private String lastName;

    @Getter @Setter
    @NotBlank(message = "email can't be empty")
    @Email(message = "email not valid")
    @Column("email")
    private String email;

    @Getter @Setter
    @NotBlank(message = "password can't be empty")
    @Column("password")
    private String password;

    @MappedCollection
    private final Set<Token> tokens = new HashSet<>();

    public static User of(String firstName, String lastName, String email, String password) {
        return new User(null, firstName, lastName, email, password, Collections.emptyList());
    }

    @PersistenceCreator
    private User(Long id, String firstName, String lastName, String email, String password, Collection<Token> tokens) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.tokens.addAll(tokens);
    }

    public void addToken(Token token) {
        this.tokens.add(token);
    }

    public Boolean removeToken(Token token) {
        return this.tokens.remove(token);
    }

    public Boolean removeTokenId(Predicate<? super Token> predicate) {
        return this.tokens.removeIf(predicate);
    }
}
