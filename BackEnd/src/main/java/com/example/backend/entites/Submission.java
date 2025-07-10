package com.example.backend.entites;

import com.example.backend.enums.SubmissionState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Submission implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentEmail;
    private String submissionDetails; // Assuming JSON stored as a String
    private Double score;
    private String feedback;

    @Enumerated(EnumType.STRING)
    private SubmissionState state;




    @ManyToOne
    private Session sessionSB;

    // Additional fields and methods
}
