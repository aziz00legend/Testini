package com.example.backend.dtos;

import com.example.backend.enums.SubmissionState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SessionNodeDto {

    private DataNodeSession data  ;

    private List<SessionNodeDto> children;







}
