package com.example.backend.mappers;



import com.example.backend.dtos.SessionDTO;
import com.example.backend.dtos.Result;
import com.example.backend.dtos.SessionSaveUpdateDTO;
import com.example.backend.entites.Session;
import com.example.backend.entites.Submission;
import com.example.backend.dtos.SubmissionDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubmissionMapper {


    public Submission toSubmissionFromSubmissionDTO(SubmissionDTO submissionDTO)
    {
        Submission submission = new Submission();
        submission.setId(submissionDTO.getId());
        submission.setSubmissionDetails(submissionDTO.getSubmissionDetails());
        submission.setStudentEmail(submissionDTO.getStudentEmail());
        submission.setFeedback(submissionDTO.getFeedback());
        submission.setScore(submissionDTO.getScore());
        submission.setState(submissionDTO.getState());
        return submission;
    }
    public SubmissionDTO toSubmissionDTOFromSubmission(Submission submission)
    {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(submission.getId());
        submissionDTO.setSubmissionDetails(submission.getSubmissionDetails());
        submissionDTO.setStudentEmail(submission.getStudentEmail());
        submissionDTO.setFeedback(submission.getFeedback());
        submissionDTO.setScore(submission.getScore());
        submissionDTO.setState(submission.getState());
        return submissionDTO;
    }



}
