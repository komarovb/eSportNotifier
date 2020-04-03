package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.Notification;
import com.komarov.patel.research.methodology.esportservice.model.User;
import com.komarov.patel.research.methodology.esportservice.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    EmailService emailService;

    @Override
    public void setNotification(User user, long matchId, long teamId) {
        logger.debug("Creating a notification!");
        Notification test = notificationRepository.findByTeamIdAndMatchId(teamId, matchId);
        if (test == null) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMatchId(matchId);
            notification.setTeamId(teamId);
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

    @Override
    public int checkAllNotifications() {
        int count = 0;
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


}
