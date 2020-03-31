package com.komarov.patel.research.methodology.esportservice.controllers;

import com.komarov.patel.research.methodology.esportservice.model.Game;
import com.komarov.patel.research.methodology.esportservice.model.Match;
import com.komarov.patel.research.methodology.esportservice.model.User;
import com.komarov.patel.research.methodology.esportservice.repository.GameRepository;
import com.komarov.patel.research.methodology.esportservice.repository.UserRepository;
import com.komarov.patel.research.methodology.esportservice.service.DataSourceService;
import com.komarov.patel.research.methodology.esportservice.service.UserService;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    DataSourceService  pandaService;

    @Autowired
    UserService userService;

    @Autowired
    GameRepository gameRepository;

    /**
     * Public root endpoint returning the next 10 matches for all eSport disciplines
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Game> games = pandaService.upcomingMatches();
        if(games != null) {
            model.addAttribute("games", games);
            model.addAttribute("errors", false);
        } else {
            model.addAttribute("errors", true);
        }
        return "index";
    }

    /**
     * Public endpoint returning the next and previous matches for a selected eSport discipline
     */
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.GET)
    public String getMatches(Model model, @PathVariable(value = "gameId") long gameId, @RequestParam(required = false) String pageNum) {
        int page = 1;
        if(pageNum != null) {
            page = Integer.parseInt(pageNum);
        }
        List<Match> upcomingMatches = pandaService.upcomingMatches(gameId, page);
        List<Match> pastMatches = pandaService.pastMatches(gameId, page);
        if(upcomingMatches!= null && pastMatches != null) {
            model.addAttribute("upcomingMatches", upcomingMatches);
            model.addAttribute("pastMatches", pastMatches);
            model.addAttribute("game", gameRepository.findById(gameId));
            model.addAttribute("errors", false);
        } else {
            model.addAttribute("errors", true);
        }
        return "gamePage";
    }

}
