package com.examportal.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(length = 25, unique = true)
    private String username;

    @Column(length = 25)
    private String firstName;

    @Column(length = 25)
    private String lastName;


    @Column(length = 64, unique = true)
    private String email;

    @Column(length = 64)
    private String password;

    @Column(length = 25)
    private String phone;

    private Boolean enabled = true;

    private String profile = "test.png";


    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;


    public User(String username, String firstName, String lastName, String email, String password, String phone) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
