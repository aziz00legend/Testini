package com.example.backend.repositoris;

import com.example.backend.entites.Classroom;
import com.example.backend.entites.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByTitle(String title);
    @Query("SELECT c FROM Classroom c WHERE c.instructorCL.id = :instructorId")
    List<Classroom> findByInstructorId(@Param("instructorId") Long instructorId);
}
