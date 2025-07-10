package com.example.backend.services;

import com.example.backend.entites.Submission;
import com.example.backend.dtos.SubmissionDTO;

import java.util.List;

public interface SubmissionService {
    void saveSubmission(SubmissionDTO submissionDTO, Long sessionId);

    Void updateSubmission(SubmissionDTO submissionDTO);

    List<Submission> getAllSubmissions();
    SubmissionDTO getSubmissionById(Long submissionId);
    void deleteSubmission(Long submissionId);
}
