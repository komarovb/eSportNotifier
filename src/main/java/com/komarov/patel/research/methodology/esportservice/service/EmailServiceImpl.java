package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.User;
import com.komarov.patel.research.methodology.esportservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService{

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    UserRepository userRepository;

    @Override
    public void sendSimpleEmail(String userEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendInitialNotification(User user) {
        this.sendSimpleEmail(user.getUsername(), "First Notification", "If you are able to see this notification then you will be able to receive notifications from us in the future!");
        user.setInitialNotificationSent(true);
        userRepository.save(user);
    }

    @Override
    public void sendNotification(User user) {
        this.sendSimpleEmail(user.getUsername(), "eSports Notification", "Notification!");
        user.setInitialNotificationSent(true);
        userRepository.save(user);
    }
}
