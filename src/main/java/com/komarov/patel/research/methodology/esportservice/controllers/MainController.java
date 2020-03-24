package com.komarov.patel.research.methodology.esportservice.controllers;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class MainController {

    @Value("${data.source.panda-api-token}")
    private String apiToken;

    @Value("${data.source.panda-url}")
    private String pandaUrl;

    /**
     * Public root endpoint returning the next 10 matches for all eSport disciplines
     */
    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return getData();
    }

    /**
     * Public endpoint returning the next matches for a selected eSport discipline
     */
    @RequestMapping("/upcoming/{gameId}")
    @ResponseBody
    public String getUpcomingMatches(@PathVariable(value = "gameId") String gameId) {
        return "Greetings from the game: " + gameId + "!";
    }

    private String getData() {
        StringBuilder content = new StringBuilder();
        if(apiToken != null) {
            try {
                URL url = new URL(pandaUrl + "csgo/matches/upcoming?token=" + apiToken);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                JSONParser parse = new JSONParser();
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
