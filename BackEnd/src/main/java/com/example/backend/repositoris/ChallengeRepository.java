package com.example.backend.repositoris;

import com.example.backend.entites.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByTitle(String title);
    long countByInstructorCH_Id(Long instructorId);

    List<Challenge> findByInstructorCH_Id(Long instructorId);
}