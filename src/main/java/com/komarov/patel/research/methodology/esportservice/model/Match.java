package com.komarov.patel.research.methodology.esportservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Match {
    // Match properties
    private Date beginAt;
    private Date endAt;
    private String winner;
    private String winnerId;
    private String name;
    private String matchType;

    private List<Team> teams;

    // League properties
    private long leagueId;
    private String leagueName;
    private String leagueImg;
}
