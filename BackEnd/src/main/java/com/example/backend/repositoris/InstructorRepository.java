package com.example.backend.repositoris;

import com.example.backend.entites.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findByEmail(String email);
}
