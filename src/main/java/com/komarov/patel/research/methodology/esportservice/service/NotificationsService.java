package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.Notification;
import com.komarov.patel.research.methodology.esportservice.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface NotificationsService {
    void setNotification(User user, long matchId, long teamId, long gameId, Date beginAt, String matchName);
    void deleteNotification(User user, long matchId, long teamId);

    int checkAllNotifications();

    HashMap<Long, Notification> organizeNotifications(List<Notification> notifications);
}
