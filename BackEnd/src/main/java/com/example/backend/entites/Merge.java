package com.example.backend.entites;

import com.example.backend.enums.OperationM;
import com.example.backend.enums.Privilege;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Merge implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "shared_generator")
    @TableGenerator(
            name = "shared_generator",
            table = "shared_id_generator",
            pkColumnName = "entity_name",
            valueColumnName = "next_id",
            pkColumnValue = "shared_id",
            allocationSize = 1
    )
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private OperationM operation;

    @ElementCollection
    private List<Long> idSessions;

    @ElementCollection
    private List<Integer> coefSessions;

    @ManyToOne
    private Classroom classroomMerge;
}
