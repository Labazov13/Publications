package com.example.Publications.services;

import com.example.Publications.models.Publication;
import com.example.Publications.models.PublicationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PublicationService {
    @Value("${rabbit.exchange.name}")
    private String exchange;
    @Value("${rabbit.key.publications}")
    private String routingKey;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public PublicationService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public Publication sendPublication(PublicationDTO publicationDTO) throws JsonProcessingException {
        Publication publication = new Publication(UUID.randomUUID(), publicationDTO.username(), publicationDTO.message());
        String jsonPublication = objectMapper.writeValueAsString(publication);
        rabbitTemplate.convertAndSend(exchange, routingKey, jsonPublication);
        return publication;
    }
}
