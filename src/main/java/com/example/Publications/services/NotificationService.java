package com.example.Publications.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = "${rabbit.queue.notifications}")
    public void receiveNotification(String message) {
        LOGGER.info(message);
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()){
            LOGGER.info("Notification sent to user:" + matcher.group(1));
        }
        else {
            LOGGER.error("User not found!");
        }
    }
}
