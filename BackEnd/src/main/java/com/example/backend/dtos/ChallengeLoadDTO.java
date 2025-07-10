package com.example.backend.dtos;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChallengeLoadDTO {
    private String title;
    private String description;
    private String templateName;
    private List<Map<String, Object>> questions;
}
