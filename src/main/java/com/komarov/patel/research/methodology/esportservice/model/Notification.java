package com.komarov.patel.research.methodology.esportservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long teamId;
    private long matchId;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
