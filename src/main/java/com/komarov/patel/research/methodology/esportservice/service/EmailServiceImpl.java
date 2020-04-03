package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.Match;
import com.komarov.patel.research.methodology.esportservice.model.Notification;
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
    public boolean sendNotification(Notification notification, Match match, boolean postponed) {
        User user = notification.getUser();
        if(user != null) {
            String normalMsg = "Hello " + user.getName() + "!\n" +
                    "The time for one of your notification has come! Please see the details below.\n\n" +
                    "Match details:\n" +
                    notification.getMatchName() + " is going to start soon! Hurry up to your favorite broadcasting channel!\n" +
                    "Anticipated start time: " + notification.getMatchStart() + "\n\n" +
                    "Thank you!";

            String postponedMsg = "Hello " + user.getName() + "!\n" +
                    "The time for one of your notification has come! Please see the details below.\n\n" +
                    "Match details:\n" +
                    notification.getMatchName()  + " was postponed/cancelled! Please check our app to see the new match time and set a new notification!\n" +
                    "Anticipated start time: " + notification.getMatchStart() + "\n\n" +
                    "Thank you!";

            // Selecting which message to send
            String msg = postponed ? postponedMsg : normalMsg;
            this.sendSimpleEmail(user.getUsername(), "eSports Notification", msg);
            return true;
        }
        return false;
    }
}
