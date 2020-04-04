package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.Match;
import com.komarov.patel.research.methodology.esportservice.model.Notification;
import com.komarov.patel.research.methodology.esportservice.model.User;
import com.komarov.patel.research.methodology.esportservice.repository.GameRepository;
import com.komarov.patel.research.methodology.esportservice.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    DataSourceService  pandaService;

    @Autowired
    EmailService emailService;

    @Override
    public void setNotification(User user, long matchId, long teamId, long gameId, Date beginAt, String matchName) {
        logger.debug("Creating a notification!");
        Notification test = notificationRepository.findByTeamIdAndMatchId(teamId, matchId);
        if (test == null) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMatchId(matchId);
            notification.setTeamId(teamId);
            notification.setGameId(gameId);
            notification.setMatchStart(beginAt);
            notification.setMatchName(matchName);
            notificationRepository.save(notification);
            user.getNotifications().add(notification);

            if(!user.isInitialNotificationSent()){
                emailService.sendInitialNotification(user);
            }
        }
    }

    @Override
    public void deleteNotification(User user, long matchId, long teamId) {
        logger.debug("Deleting a notification!");
        Notification notification = notificationRepository.findByTeamIdAndMatchId(teamId, matchId);
        notificationRepository.delete(notification);
    }

    /**
     *  Algorithm:
     *      1. Load all notifications
     *      2. If there are any which have match such that it starts within the next 10 minutes
     *      3. Fetch upcoming matches from the datasource and check if match still exists
     *      4. If the match was postponed/cancelled send information email and delete notification
     *      5. If the match still exists send the notification emails and delete notification
     * @return Number of emails that have been sent
     */
    @Override
    public int checkAllNotifications() {
        int count = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 11); // Adding 11 minutes to the current time in order to see which notifications might be affected
        List<Notification> notifications = notificationRepository.findAllByMatchStartBefore(calendar.getTime());
        if(notifications != null && notifications.size() > 0){
            boolean postponed = false;
            HashMap<Long, HashMap<Long, Match>> matchesPerGame = new HashMap<>();
            for(Notification notification : notifications) {
                long gameId = notification.getGameId();
                if(matchesPerGame.get(gameId) == null || matchesPerGame.get(gameId).isEmpty()) {
                    List<Match> matches = pandaService.upcomingMatches(gameId, 1);
                    if(matches != null) matchesPerGame.put(gameId, matchesToMap(matches));
                    else return -1;
                }
                HashMap<Long, Match> matches = matchesPerGame.get(gameId);
                Match anticipatedMatch = matches.get(notification.getMatchId());
                if(anticipatedMatch == null || anticipatedMatch.getBeginAt().compareTo(notification.getMatchStart()) != 0) {
                    postponed = true;
                }
                boolean wasSent = emailService.sendNotification(notification, anticipatedMatch, postponed);
                if(wasSent) {
                    count++;
                    notificationRepository.delete(notification);
                }
            }
        }
        logger.info("Finishing notification check job {} notification email were sent!", count);
        return count;
    }

    @Override
    public HashMap<Long, Notification> organizeNotifications(List<Notification> notifications) {
        HashMap<Long, Notification> organizedNotifications = new HashMap<>();
        for(Notification notification : notifications){
            organizedNotifications.put(notification.getMatchId(), notification);
        }
        return organizedNotifications;
    }

    private HashMap<Long, Match> matchesToMap(List<Match> matches) {
        HashMap<Long, Match> map = new HashMap<>();
        if(matches == null || matches.size() == 0) return map;
        for(Match match : matches) {
            map.put(match.getId(), match);
        }
        return map;
    }

}
