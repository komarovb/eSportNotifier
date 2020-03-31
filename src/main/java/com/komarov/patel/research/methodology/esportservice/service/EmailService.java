package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.User;

public interface EmailService {
    void sendSimpleEmail(String userEmail, String subject, String text);

    void sendInitialNotification(User user);
}
