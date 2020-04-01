package com.komarov.patel.research.methodology.esportservice.controllers;

import com.komarov.patel.research.methodology.esportservice.model.*;
import com.komarov.patel.research.methodology.esportservice.repository.GameRepository;
import com.komarov.patel.research.methodology.esportservice.repository.UserRepository;
import com.komarov.patel.research.methodology.esportservice.service.DataSourceService;
import com.komarov.patel.research.methodology.esportservice.service.EmailService;
import com.komarov.patel.research.methodology.esportservice.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
public class RegisteredController {

    @Autowired
    DataSourceService pandaService;

    @Autowired
    NotificationsService notificationsService;

    @Autowired
    EmailService emailService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/registered/notifications", method = RequestMethod.GET)
    @ResponseBody
    public String getNotifications() {
        return "All of your notification would show here!";
    }

    @RequestMapping(value = "/registered/notifications", method = RequestMethod.POST)
    public String setNotification(@RequestParam(value="teamId") long teamId, @RequestParam(value="matchId") long matchId,
                                  HttpServletRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        notificationsService.setNotification(user, matchId, teamId);
        if(!user.isInitialNotificationSent()){
            emailService.sendInitialNotification(user);
        }
        // Trick to redirect back to the previous URL
        return "redirect:" + request.getHeader("Referer");
//        return "redirect:/registered/notifications";
    }

    @RequestMapping(value = "/registered/notifications/delete", method = RequestMethod.POST)
    public String deleteNotification(@RequestParam(value="teamId") long teamId, @RequestParam(value="matchId") long matchId,
                                     HttpServletRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        notificationsService.deleteNotification(user, matchId, teamId);

        // Trick to redirect back to the previous URL
        return "redirect:" + request.getHeader("Referer");
//        return "redirect:/registered/notifications";
    }

    @RequestMapping(value = "/registered/{gameId}/{teamId}", method = RequestMethod.GET)
    public String getGamesForTeam(Model model, @PathVariable(value="gameId") long gameId, @PathVariable(value="teamId")
            long teamId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Game game = gameRepository.findById(gameId);
        Team team = pandaService.getTeam(game, teamId);
        List<Notification> notifications = user.getNotifications();
        HashMap<Long, Notification> organizedNotifications = notificationsService.organizeNotifications(notifications);
        List<Match> upcomingMatches = pandaService.upcomingMatches(game, team);
        List<Match> pastMatches = pandaService.pastMatches(game, team);

        if(upcomingMatches!= null && pastMatches != null) {
            model.addAttribute("upcomingMatches", upcomingMatches);
            model.addAttribute("pastMatches", pastMatches);
            model.addAttribute("notifications", organizedNotifications);
            model.addAttribute("team", team);
            model.addAttribute("game", game);
            model.addAttribute("errors", false);
        } else {
            model.addAttribute("errors", true);
        }
        return "teamPage";
    }
}
