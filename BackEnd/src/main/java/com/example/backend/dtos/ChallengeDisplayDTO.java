package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDisplayDTO {
    private Long id;
    private String title;
    private int numQuestions;
    private String status;
    private String creationDate;
    private int numberOfUses;
    private String templateName;
}
