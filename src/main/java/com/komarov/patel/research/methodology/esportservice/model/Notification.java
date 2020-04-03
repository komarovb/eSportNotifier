package com.komarov.patel.research.methodology.esportservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long teamId;
    private long gameId;
    private long matchId;
    private Date matchStart;
    private String matchName;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
