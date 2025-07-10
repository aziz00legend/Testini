package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    // Setters
    // Getters
    private Long id;
    private String title;
    private List<Result> results;


   

    // Override equals() and hashCode() for correct comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDTO that = (SessionDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, results);
    }

    // Override toString() for a more readable representation
    @Override
    public String toString() {
        return "SessionDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", results=" + results +
                '}';
    }
}
