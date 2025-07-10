package com.example.backend.mappers;



import com.example.backend.dtos.DataNodeSession;
import com.example.backend.dtos.SessionDTO;
import com.example.backend.dtos.Result;
import com.example.backend.dtos.SessionSaveUpdateDTO;
import com.example.backend.entites.Session;
import com.example.backend.entites.Submission;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SessionMapper {

    public SessionDTO toSessionDTO(Session session) {
        List<Result> submissionDTOs = session.getSubmissions().stream()
                .map(this::toResultFromSubmission)
                .collect(Collectors.toList());

        return new SessionDTO(
                session.getId(),
                session.getTitle(),
                submissionDTOs
        );
    }

    private Result toResultFromSubmission(Submission submission) {
        return new Result(
                submission.getId(),
                submission.getScore(),
                submission.getStudentEmail()
        );
    }


    public  Session toSessionFromSessionSaveUpdateDTO(SessionSaveUpdateDTO sessionSaveUpdateDTO)
    {
        Session session = new Session();
        session.setId(sessionSaveUpdateDTO.getId());
        session.setTitle(sessionSaveUpdateDTO.getTitle());
        session.setStartTime(sessionSaveUpdateDTO.getStartTime());
        session.setEndTime(sessionSaveUpdateDTO.getEndTime());
        session.setSessionCode(sessionSaveUpdateDTO.getSessionCode());
        session.setCreatedAt(sessionSaveUpdateDTO.getCreatedAt());
        return session;

    }





    public DataNodeSession toNodeSessionData(SessionDTO sessionDTO) {
        // Map results to student email as key
        Map<String, Map<String, Object>> studentMap = sessionDTO.getResults().stream()
                .collect(Collectors.toMap(
                        Result::getStudentEmail,
                        result -> {
                            Map<String, Object> studentDetails = new HashMap<>();
                            studentDetails.put("id", result.getId());
                            studentDetails.put("score", result.getScore());
                            return studentDetails;
                        },
                        (existing, replacement) -> replacement // Handle duplicate emails
                ));

        // Create NodeSubtionData
        return new DataNodeSession(sessionDTO.getId(), sessionDTO.getTitle(), studentMap);
    }
}
