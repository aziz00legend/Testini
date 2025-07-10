package com.example.backend.dtos;

import com.example.backend.entites.Challenge;
import com.example.backend.entites.Classroom;
import com.example.backend.entites.Submission;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;



@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SessionSaveUpdateDTO  {

    private Long id;
    private String title;


    private String sessionCode;


    private Date startTime;

    private Date endTime;

    private Date createdAt;




}
