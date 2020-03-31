package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.Game;
import com.komarov.patel.research.methodology.esportservice.model.Match;
import com.komarov.patel.research.methodology.esportservice.model.Team;

import java.util.HashMap;
import java.util.List;

public interface DataSourceService {
    List<Game> upcomingMatches();
    List<Match> upcomingMatches(Game game, HashMap<String, String> params);
    List<Match> upcomingMatches(Game game, Team team);

    List<Game> pastMatches();
    List<Match> pastMatches(Game game, HashMap<String, String> params);
    List<Match> pastMatches(Game game, Team team);
}
