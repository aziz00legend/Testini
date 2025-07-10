package com.example.backend.web;

import com.example.backend.dtos.ChallengeDTO;
import com.example.backend.dtos.ChallengeDisplayDTO;
import com.example.backend.dtos.ChallengeLoadDTO;
import com.example.backend.entites.Challenge;
import com.example.backend.mappers.ChallengeLoadMapper;
import com.example.backend.mappers.ChallengeMapper;
import com.example.backend.services.ChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;
    private final ChallengeMapper challengeMapper;
    private final ChallengeLoadMapper challengeLoadMapper;

    @PostMapping("/empty")
    public ResponseEntity<Map<String, Object>> createEmptyChallenge(@RequestParam Long instructorId) {
        try {
            // Create the new challenge
            Challenge newChallenge = challengeService.createEmptyChallenge(instructorId);

            // Prepare the response
            Map<String, Object> response = new HashMap<>();
            response.put("id", newChallenge.getId());
            response.put("message", "Empty challenge created successfully.");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Endpoint to load challenge by id and validate instructor
    @GetMapping("/load/{id}")
    public ResponseEntity<?> loadChallengeById(@PathVariable Long id, @RequestParam Long instructorId) {
        try {
            // Fetch the challenge using the service method that checks the instructor
            Challenge challenge = challengeService.getChallengeByIdWithInstructor(id, instructorId);

            // Map the Challenge entity to the appropriate DTO for loading
            ChallengeLoadDTO challengeLoadDTO = challengeLoadMapper.convertToChallengeLoadDTO(challenge);
            System.out.println(challengeLoadDTO);

            return ResponseEntity.ok(challengeLoadDTO);  // Return the loaded DTO
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Error: " + e.getMessage());  // Return error message if instructor ID mismatch occurs
        }
    }

    @PostMapping("/save/{challengeId}")
    public ResponseEntity<Map<String, String>> createOrUpdateChallenge(
            @PathVariable Long challengeId,
            @RequestBody ChallengeDTO challengeDTO,
            @RequestParam Long instructorId) {

        try {
            // Convert the DTO to the Challenge entity
            Challenge challenge = challengeMapper.convertToChallenge(challengeDTO, instructorId);
            challenge.setId(challengeId);

            // Call the service to save the challenge (service method returns void)
            challengeService.saveChallenge(challenge, instructorId);

            // Prepare success response
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Challenge created/updated successfully.");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Prepare error response in case of an exception
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create/update challenge: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/publish/{challengeId}")
    public ResponseEntity<Map<String, Object>> publishChallenge(@PathVariable Long challengeId) {
        Map<String, Object> response = new HashMap<>();
        try {
            challengeService.publishChallenge(challengeId);
            response.put("status", "success");
            response.put("message", "Challenge published successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to publish challenge: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/display/{instructorId}")
    public List<ChallengeDisplayDTO> getInstructorChallengesForDisplay(@PathVariable Long instructorId) {
        // Fetch challenges for the instructor from the service
        List<Challenge> challenges = challengeService.getAllChallengesByInstructor(instructorId);

        System.out.println((instructorId));
        // Use the mapper to convert entities to DTOs
        return challenges.stream()
                .map(challengeMapper::toChallengeDisplayDTO)
                .toList();
    }


    @GetMapping
    public ResponseEntity<List<ChallengeDTO>> getAllChallenges() {
        List<Challenge> challenges = challengeService.getAllChallenges();
        List<ChallengeDTO> challengeDTOs = challenges.stream()
                .map(challengeMapper::convertToChallengeDTO)
                .toList();
        return new ResponseEntity<>(challengeDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDTO> getChallengeById(@PathVariable Long id) {
        Challenge challenge = challengeService.getChallengeById(id);
        ChallengeDTO challengeDTO = challengeMapper.convertToChallengeDTO(challenge);
        return new ResponseEntity<>(challengeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
