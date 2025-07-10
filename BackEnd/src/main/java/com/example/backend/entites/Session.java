package com.example.backend.entites;

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
public class Session implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "shared_generator")
    private Long id;

    private String title;

    private String sessionCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ElementCollection
    private List<String> participantEmails;

    @ManyToOne
    private Challenge challengeSH;

    @ManyToOne
    private Classroom classroomSH;

    @OneToMany(mappedBy = "sessionSB")
    private List<Submission> Submissions;
}
