package com.komarov.patel.research.methodology.esportservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    private boolean initialNotificationSent = false;


    @OneToMany(mappedBy="user")
    private List<Notification> notifications;

    @Transient
    private String passwordConfirm;
}
