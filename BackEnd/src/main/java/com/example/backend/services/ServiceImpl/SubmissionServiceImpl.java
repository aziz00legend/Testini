package com.example.backend.services.ServiceImpl;

import com.example.backend.entites.Submission;
import com.example.backend.entites.Session;
import com.example.backend.dtos.SubmissionDTO;
import com.example.backend.mappers.SubmissionMapper;
import com.example.backend.repositoris.SubmissionRepository;
import com.example.backend.repositoris.SessionRepository;
import com.example.backend.services.SubmissionService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final SessionRepository sessionRepository;
    private final SubmissionMapper submissionMapper;

    @Override
    public void saveSubmission(SubmissionDTO submissionDTO, Long sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Submission submission =submissionMapper.toSubmissionFromSubmissionDTO(submissionDTO);
            submission.setSessionSB(sessionOptional.get());
            submissionRepository.save(submission);
        } else {
            throw new IllegalArgumentException("Session not found with ID: " + sessionId);
        }
    }
    @Override
    public Void updateSubmission(SubmissionDTO submissionDTO)
    {
        Submission submission = submissionMapper.toSubmissionFromSubmissionDTO(submissionDTO);
        Submission OldSubmission = submissionRepository.findById(submission.getId()).orElse(null);
        if (OldSubmission != null) {
            OldSubmission.setSessionSB(submission.getSessionSB());
            submissionRepository.save(OldSubmission);

        }
        return null;

    }

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public SubmissionDTO getSubmissionById(Long submissionId) {
        Optional<Submission> submissionOptional = submissionRepository.findById(submissionId);
        return submissionOptional.map(submissionMapper::toSubmissionDTOFromSubmission).orElse(null);

    }



    @Override
    public void deleteSubmission(Long submissionId) {
        if (submissionRepository.existsById(submissionId)) {
            submissionRepository.deleteById(submissionId);
        } else {
            throw new IllegalArgumentException("Submission not found with ID: " + submissionId);
        }
    }
}
