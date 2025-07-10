package com.example.backend.dtos;

import com.example.backend.enums.SubmissionState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {

    private Long id;

    private String studentEmail;
    private String submissionDetails; // Assuming JSON stored as a String
    private Double score;
    private String feedback;

    private SubmissionState state;





}
