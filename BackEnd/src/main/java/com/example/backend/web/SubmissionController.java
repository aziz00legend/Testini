package com.example.backend.web;

import com.example.backend.dtos.SubmissionDTO;
import com.example.backend.services.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubmissionController {

    private final SubmissionService submissionService;

    // Create a new submission
    @PostMapping
    public ResponseEntity<String> createSubmission(@RequestBody SubmissionDTO submissionDTO,
                                                   @RequestParam Long sessionId) {
        try {
            submissionService.saveSubmission(submissionDTO, sessionId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Submission created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Update an existing submission
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSubmission(@PathVariable Long id,
                                                   @Valid @RequestBody SubmissionDTO submissionDTO) {
        try {
            submissionDTO.setId(id); // Ensure the ID is set before updating
            submissionService.updateSubmission(submissionDTO);
            return ResponseEntity.ok("Submission updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Retrieve a submission by ID
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long id) {
        SubmissionDTO submissionDTO = submissionService.getSubmissionById(id);
        if (submissionDTO != null) {
            return ResponseEntity.ok(submissionDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Retrieve all submissions
    @GetMapping("/all")
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions() {
        List<SubmissionDTO> submissions = submissionService.getAllSubmissions().stream()
                .map(submission -> submissionService.getSubmissionById(submission.getId()))
                .toList();
        return ResponseEntity.ok(submissions);
    }

    // Delete a submission by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubmission(@PathVariable Long id) {
        try {
            submissionService.deleteSubmission(id);
            return ResponseEntity.ok("Submission deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
