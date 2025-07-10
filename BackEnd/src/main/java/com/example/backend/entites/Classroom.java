package com.example.backend.entites;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Classroom implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String titleColor;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;




    @ElementCollection
    private List<Float> coefs;

    @ManyToOne
    private Instructor instructorCL;
    @OneToMany(mappedBy = "classroomSH")
    private List<Session> sessions;
    @OneToMany(mappedBy = "classroomMerge")
    private List<Merge> mergingDetails;




    public Set<String> getDistinctStudentEmails() {
        if (sessions == null || sessions.isEmpty()) {
            return Collections.emptySet();
        }

        return sessions.stream()
                .flatMap(session -> session.getSubmissions().stream())
                .map(Submission::getStudentEmail)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }



    public Session getClosestUpcomingSession() {
        Date now = new Date();

        // Check if the list of sessions is null or empty
        return sessions == null || sessions.isEmpty() ? // Changed to 'sessions'
                null :
                sessions.stream() // Changed to 'sessions'
                        .filter(session -> session.getStartTime().after(now))
                        .min(Comparator.comparing(Session::getStartTime))
                        .orElse(null);
    }



}
