package com.komarov.patel.research.methodology.esportservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;

    private String name;

    private String password;

    @Transient
    private String passwordConfirm;
}
