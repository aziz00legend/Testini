package com.example.backend.entites;

import com.example.backend.enums.ChallengeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Challenge implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(columnDefinition = "LONGTEXT")
    private String challengeData; // Assuming JSON stored as a String
    private String templateName;

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status; // New status field with enum type

    @ManyToOne
    private Instructor instructorCH;
    @OneToMany(mappedBy = "challengeSH")
    private List<Session> sessions;

}
