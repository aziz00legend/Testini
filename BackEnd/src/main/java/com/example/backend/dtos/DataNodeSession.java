package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataNodeSession {

    private Long id; // Session ID
    private String title; // Session Title
    private Map<String, Map<String, Object>> students; // Students mapped by email with id and score
}
