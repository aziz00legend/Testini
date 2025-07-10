package com.example.backend.dtos;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    // Getters
    private Long id;
    private Double score;
    private String studentEmail;




    // Override equals() and hashCode() for proper comparison and hashing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(id, result.id) &&
                Objects.equals(score, result.score) &&
                Objects.equals(studentEmail, result.studentEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, studentEmail);
    }

    // Override toString() for a readable string representation
    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", score=" + score +
                ", studentEmail='" + studentEmail + '\'' +
                '}';
    }
}
