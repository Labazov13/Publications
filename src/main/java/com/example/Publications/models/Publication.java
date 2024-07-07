package com.example.Publications.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Publication implements Serializable {
    private UUID id;
    private String username;
    private String message;

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
