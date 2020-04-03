package com.komarov.patel.research.methodology.esportservice.repository;

import com.komarov.patel.research.methodology.esportservice.model.Game;
import com.komarov.patel.research.methodology.esportservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findById(long notificationId);
    Notification findByTeamIdAndMatchId(long teamId, long matchId);

    List<Notification> findAllByMatchStartBefore(Date matchStart);
}
