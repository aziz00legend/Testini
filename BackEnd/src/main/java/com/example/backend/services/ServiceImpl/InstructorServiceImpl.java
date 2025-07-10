package com.example.backend.services.ServiceImpl;

import com.example.backend.entites.Instructor;
import com.example.backend.repositoris.InstructorRepository;
import com.example.backend.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;

    @Override
    public void saveInstructor(Instructor instructor) {
        instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor getInstructorById(Long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with ID: " + instructorId));
    }

    @Override
    public void deleteInstructor(Long instructorId) {
        if (instructorRepository.existsById(instructorId)) {
            instructorRepository.deleteById(instructorId);
        } else {
            throw new IllegalArgumentException("Instructor not found with ID: " + instructorId);
        }
    }
}
