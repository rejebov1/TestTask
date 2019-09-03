package com.hiendsys.test.dao.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user",schema = "hiendsys")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@Length(min = 3,max = 16)*/
    @Column(name = "username",unique = true)
    private String username;

    /*@Length(min = 3,max = 16)*/
    @Column(name = "password",updatable = false)
    private String password;

    /*@Length(min = 1,max = 16)*/
    @Column(name = "first_name")
    private String firstName;

    /*@Length(min = 1,max = 16)*/
    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "status")
    private Boolean isActive;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Role{
        USER,ADMIN
    }
}
