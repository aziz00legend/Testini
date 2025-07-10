package com.example.backend.repositoris;

import com.example.backend.entites.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findBySessionSBId(Long sessionId); // To find submissions by session ID
}
