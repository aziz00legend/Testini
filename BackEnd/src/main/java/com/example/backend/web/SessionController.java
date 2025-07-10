package com.example.backend.web;

import com.example.backend.dtos.ClassroomUpdateSaveDTO;
import com.example.backend.dtos.SessionDTO;
import com.example.backend.dtos.SessionNodeDto;
import com.example.backend.dtos.SessionSaveUpdateDTO;
import com.example.backend.mappers.SessionMapper;
import com.example.backend.services.SessionService;


import com.example.backend.entites.Session;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sessions")
@CrossOrigin("*")
public class SessionController {

    private final SessionService sessionService;
    private final SessionMapper sessionMapper;

    // Récupérer toutes les sessions et les transformer en DTO
    @GetMapping("/all")
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        long a=1L;
        return ResponseEntity.ok(sessionService.getSessionsDTOByClassroomId(a));
    }

    // Récupérer une session par ID et la transformer en DTO
    @GetMapping("/{id}")
    public ResponseEntity<SessionNodeDto> getSessionById(@PathVariable Long id) {
        SessionNodeDto session = sessionService.getSessionById(id);
        return ResponseEntity.ok(session);
    }

    // Créer une nouvelle session
    @PostMapping
    public ResponseEntity<Void> createSession(@RequestBody SessionSaveUpdateDTO sessionSaveUpdateDto,
                                              @RequestParam Long challengeId,
                                              @RequestParam Long classroomId) {
        sessionService.saveSession(sessionSaveUpdateDto, challengeId, classroomId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSession(@PathVariable Long id, @RequestBody SessionSaveUpdateDTO sessionSaveUpdateDto) {
        try {
            sessionSaveUpdateDto.setId(id);
            sessionService.updateSession(sessionSaveUpdateDto);
            return ResponseEntity.ok("Classroom updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Supprimer une session par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{classroomId}/sessions")
    public ResponseEntity<List<SessionDTO>> getSessionsByClassroomId(@PathVariable Long classroomId) {
        try {
            // Fetch the sessions from the service layer
            List<SessionDTO> sessionDTOS = sessionService.getSessionsDTOByClassroomId(classroomId);

            // Return the response with the list of sessions
            return ResponseEntity.ok(sessionDTOS);
        } catch (EntityNotFoundException ex) {
            // If classroom not found, return 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            // Handle any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }









    @GetMapping("/treenization/{classroomId}")
    public ResponseEntity<List<SessionNodeDto>> treenizationSessionByClassroomId(@PathVariable Long classroomId) {
        try {
            return ResponseEntity.ok(sessionService.treenizationSessionByClassroomId(classroomId));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}



