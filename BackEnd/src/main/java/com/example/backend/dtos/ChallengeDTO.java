package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ChallengeDTO {

    private String title;
    private String description;
    private String challengeData;
    private String templateName;

    private Date createdAt;
    private String status; // This could be "IN_PROGRESS", "PUBLISHED", etc.

}
