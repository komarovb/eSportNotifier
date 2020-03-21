package com.komarov.patel.research.methodology.esportservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisteredController {
    @RequestMapping("/registered/notifications")
    @ResponseBody
    public String getNotifications() {
        return "All of your notification would show here!";
    }

    @RequestMapping("/registered/{gameId}/{teamId}}")
    @ResponseBody
    public String getGamesForTeam(@PathVariable(value="gameId") String gameId, @PathVariable(value="teamId") String teamId) {
        return "All of your notification would show here!";
    }
}
