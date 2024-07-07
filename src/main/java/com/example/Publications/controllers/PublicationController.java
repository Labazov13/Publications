package com.example.Publications.controllers;

import com.example.Publications.models.Comment;
import com.example.Publications.models.PublicationDTO;
import com.example.Publications.services.ActivityService;
import com.example.Publications.services.PublicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {


    private final PublicationService publicationService;
    private final ActivityService activityService;

    @GetMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendPublication(@RequestBody PublicationDTO publicationDTO) throws JsonProcessingException {
        return ResponseEntity.ok(publicationService.sendPublication(publicationDTO).toString());
    }
    @GetMapping(value = "/like/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> like(@PathVariable String username){
        return ResponseEntity.ok(activityService.like(username));
    }
    @GetMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> comment(@RequestBody Comment comment){
        return ResponseEntity.ok(activityService.comment(comment));
    }
}
