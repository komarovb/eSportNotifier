package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.Game;
import com.komarov.patel.research.methodology.esportservice.model.Match;
import com.komarov.patel.research.methodology.esportservice.model.Team;
import com.komarov.patel.research.methodology.esportservice.repository.GameRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PandaDataSourceServiceImpl implements DataSourceService{

    @Autowired
    private GameRepository gameRepository;

    @Value("${data.source.panda-api-token}")
    private String apiToken;

    @Value("${data.source.panda-url}")
    private String dataSourceUrl;


    @Override
    public List<Game> upcomingMatches() {
        // Using the following URL:
        // https://api.pandascore.co/csgo/matches/upcoming?page[size]=10&page[number]=1&token=
        List<Game> allGames = gameRepository.findAll();
        HashMap<String, String> params = new HashMap<>();
        params.put("page[size]", "10");
        params.put("page[number]", "1");
        for (Game game : allGames) {
            List<Match> matches = upcomingMatches(game, params);
            game.setMatches(matches);
        }
        return allGames;
    }

    @Override
    public List<Match> upcomingMatches(Game game, HashMap<String, String> params) {
        String urlBit = "/matches/upcoming";
        String tokenBit = "token=" + apiToken;
        String gameIdentifier = game.getGameIdentifier();
        StringBuilder requestUrl = new StringBuilder(dataSourceUrl + gameIdentifier + urlBit + "?");
        for (String paramKey : params.keySet()) {
            requestUrl.append(paramKey).append("=").append(params.get(paramKey));
            requestUrl.append("&");
        }
        requestUrl.append(tokenBit);
        String data = getData(requestUrl.toString());
        JSONParser parser = new JSONParser();
        List<Match> matches = new ArrayList<>();
        try {
            JSONArray jsonMatches = (JSONArray)parser.parse(data);
            int numMatches = jsonMatches.size();
            for (int i = 0; i < numMatches; i++) {
                // Reading data from JSON document
                JSONObject jsonMatch = (JSONObject) jsonMatches.get(i);
                String beginAt = (String) jsonMatch.get("begin_at");
                String endAt = (String) jsonMatch.get("end_at");
                String matchType = (String) jsonMatch.get("match_type");
                long numGames = (long) jsonMatch.get("number_of_games");
                if(matchType != null && numGames != 0) {
                    matchType = matchType + " " + numGames;
                }
                String name = (String) jsonMatch.get("name");

                // Reading league information
                JSONObject jsonLeague = (JSONObject) jsonMatch.get("league");
                String leagueImageUrl = (String)jsonLeague.get("image_url");
                String leagueName = (String)jsonLeague.get("name");
                long leagueId = (long)jsonLeague.get("id");

                // Reading information about participating teams
                JSONArray jsonTeams = (JSONArray) jsonMatch.get("opponents");
                List<Team> teams = new ArrayList<>();
                int numberOfTeams = jsonTeams.size();
                for(int j = 0; j < numberOfTeams; j++) {
                    JSONObject jsonOpponent = (JSONObject) jsonTeams.get(j);
                    if(jsonOpponent.get("type").equals("Team")){
                        JSONObject jsonTeam = (JSONObject) jsonOpponent.get("opponent");
                        String teamName = (String) jsonTeam.get("name");
                        String teamImage = (String) jsonTeam.get("image_url");
                        long teamId = (long) jsonTeam.get("id");
                        teams.add(new Team(teamId, teamName, teamImage));
                    }
                }

                // Creating new Match object
                Match match = new Match();
                match.setName(name);
                match.setMatchType(matchType);
                match.setLeagueId(leagueId);
                match.setLeagueImg(leagueImageUrl);
                match.setLeagueName(leagueName);

                if(beginAt != null) {
                    Date begin = DatatypeConverter.parseDateTime(beginAt).getTime();
                    match.setBeginAt(begin);
                }

                if(endAt != null) {
                    Date end = DatatypeConverter.parseDateTime(endAt).getTime();
                    match.setEndAt(end);
                }
                matches.add(match);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return matches;
    }

    @Override
    public List<Match> upcomingMatches(Game game, Team team) {
        String urlBit = "/matches/upcoming";
        return null;
    }

    @Override
    public List<Game> pastMatches() {
        return null;
    }

    @Override
    public List<Match> pastMatches(Game game, HashMap<String, String> params) {
        String urlBit = "matches/past";
        return null;
    }

    @Override
    public List<Match> pastMatches(Game game, Team team) {
        String urlBit = "matches/past";
        return null;
    }

    private String getData(String requestString) {
        StringBuilder content = new StringBuilder();
        if(apiToken != null) {
            try {
                URL url = new URL(requestString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}
