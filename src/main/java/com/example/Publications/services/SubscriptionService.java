package com.example.Publications.services;

import com.example.Publications.models.Publication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbit.key.notifications}")
    private String key;
    @Value("${rabbit.exchange.name}")
    private String exchange;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @RabbitListener(queues = "${rabbit.queue.publications}")
    public void receivePublication(String message) throws JsonProcessingException {
        LOGGER.info(this.getClass() + " " + message);
        Publication publication = objectMapper.readValue(message, Publication.class);
        rabbitTemplate.convertAndSend(exchange, key,
                "A notification was sent to a subscriber with login: {" + publication.getUsername() + "}");
        LOGGER.info("Message send");
    }
}
