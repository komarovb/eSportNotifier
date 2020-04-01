package com.komarov.patel.research.methodology.esportservice.model;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Match {
    private long id;
    private Date beginAt;
    private Date endAt;
    private Team winner;
    private long winnerId;
    private String name;
    private String matchType;

    private List<Team> teams;

    // League properties
    private long leagueId;
    private String leagueName;
    private String leagueImg;

    public void determineWinner() {
        if(teams == null || winnerId == 0) {
            this.winner = null;
        } else {
            for(Team t : teams) {
                if(t.getId() == winnerId) winner = t;
            }
        }
    }
}
