package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.Notification;
import com.komarov.patel.research.methodology.esportservice.model.User;
import com.komarov.patel.research.methodology.esportservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void setNotification(User user, long matchId, long teamId) {
        Notification test = notificationRepository.findByTeamIdAndMatchId(teamId, matchId);
        if (test == null) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMatchId(matchId);
            notification.setTeamId(teamId);
            notificationRepository.save(notification);
            user.getNotifications().add(notification);
        }
    }

    @Override
    public HashMap<Long, Notification> organizeNotifications(List<Notification> notifications) {
        HashMap<Long, Notification> organizedNotifications = new HashMap<>();
        for(Notification notification : notifications){
            organizedNotifications.put(notification.getMatchId(), notification);
        }
        return organizedNotifications;
    }


}
