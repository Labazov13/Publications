package com.example.Publications.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Publication {
    private UUID id;
    private String username;
    private String message;
}
