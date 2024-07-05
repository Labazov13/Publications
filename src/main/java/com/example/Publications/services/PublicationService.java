package com.example.Publications.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublicationService {
    private final RabbitTemplate rabbitTemplate;


    public PublicationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPublication(String message) {
        rabbitTemplate.convertAndSend("${rabbit.key.publications}", message);
    }
}
