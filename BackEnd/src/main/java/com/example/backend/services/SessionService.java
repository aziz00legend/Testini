package com.example.backend.services;

import com.example.backend.dtos.ClassroomUpdateSaveDTO;
import com.example.backend.dtos.SessionDTO;
import com.example.backend.dtos.SessionNodeDto;
import com.example.backend.dtos.SessionSaveUpdateDTO;
import com.example.backend.entites.Session;
import org.springframework.data.util.Pair;

import java.util.List;

public interface SessionService {
    void saveSession(SessionSaveUpdateDTO sessionSaveUpdateDto, Long challengeId, Long classroomId);
    void updateSession(SessionSaveUpdateDTO sessionSaveUpdateDto);
    List<Session> getAllSessions();
    Pair<List<SessionNodeDto>, List<SessionNodeDto>> splitSessionByClassroomId(Long classroomId);
    List<SessionNodeDto> treenizationSessionByClassroomId(Long classroomId);

    List<SessionDTO> getSessionsDTOByClassroomId(Long classroomId);
    SessionNodeDto getSessionById(Long sessionId);
    void deleteSession(Long sessionId);
}
