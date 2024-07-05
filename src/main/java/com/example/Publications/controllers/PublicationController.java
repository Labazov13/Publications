package com.example.Publications.controllers;

import com.example.Publications.models.Publication;
import com.example.Publications.models.PublicationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendPublication(@RequestBody PublicationDTO publicationDTO) {
        Publication publication = new Publication(UUID.randomUUID(), publicationDTO.username(), publicationDTO.message());
        rabbitTemplate.convertAndSend("exchangeName", "routingKey", publication);
        return ResponseEntity.ok(publication.toString());
    }
}
