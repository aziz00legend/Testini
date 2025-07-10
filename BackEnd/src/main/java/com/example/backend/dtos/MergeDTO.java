package com.example.backend.dtos;

import com.example.backend.enums.OperationM;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MergeDTO {

    // Getters

    private Long id;
    private String title;
    private OperationM operation;
    private List<Long> idSessions;
    private List<Integer> coefSessions;




    // Override toString() for a more readable representation
    @Override
    public String toString() {
        return "MergeDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", operation=" + operation +
                ", iDSessions=" + idSessions +
                ", coefSessions=" + coefSessions +
                '}';
    }
}
