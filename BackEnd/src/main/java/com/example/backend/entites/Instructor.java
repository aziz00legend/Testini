package com.example.backend.entites;

import com.example.backend.enums.Privilege;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Setter;


@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Instructor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String passworld;

    @Enumerated(EnumType.STRING)
    private Privilege privilege;

    @OneToMany(mappedBy = "instructorCH")
    private List<Challenge> challenges;

    @OneToMany(mappedBy = "instructorCL")
    private List<Classroom> classrooms;

}
