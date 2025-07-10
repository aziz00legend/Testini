package com.example.backend.services;

import com.example.backend.entites.Instructor;

import java.util.List;

public interface InstructorService {
    void saveInstructor(Instructor instructor);
    List<Instructor> getAllInstructors();
    Instructor getInstructorById(Long instructorId);
    void deleteInstructor(Long instructorId);
}
