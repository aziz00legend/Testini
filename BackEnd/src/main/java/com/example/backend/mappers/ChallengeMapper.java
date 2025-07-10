package com.example.backend.mappers;

import com.example.backend.dtos.ChallengeDTO;
import com.example.backend.dtos.ChallengeDisplayDTO;
import com.example.backend.dtos.ChallengeLoadDTO;
import com.example.backend.entites.Challenge;
import com.example.backend.entites.Instructor;
import com.example.backend.enums.ChallengeStatus;
import com.example.backend.repositoris.InstructorRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ChallengeMapper {
    @Autowired
    private InstructorRepository instructorRepository;
    // Convert ChallengeDTO to Challenge
    public Challenge convertToChallenge(ChallengeDTO challengeDTO, Long instructorId) {
        Challenge challenge = new Challenge();
        challenge.setTitle(challengeDTO.getTitle());
        challenge.setDescription(challengeDTO.getDescription());
        challenge.setChallengeData(challengeDTO.getChallengeData());
        challenge.setTemplateName(challengeDTO.getTemplateName());

        // Convert status from DTO
        String status = challengeDTO.getStatus();
        if (status == null || (!status.equals("IN_PROGRESS") && !status.equals("PUBLISHED"))) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
        challenge.setStatus(ChallengeStatus.valueOf(status)); // Set status from DTO

        challenge.setCreatedAt(new Date()); // Set current time for createdAt

        // Fetch the Instructor using instructorId
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with ID: " + instructorId));

        // Set the instructor to the challenge
        challenge.setInstructorCH(instructor);

        return challenge;
    }

    // Convert Challenge entity to ChallengeDTO
    public ChallengeDTO convertToChallengeDTO(Challenge challenge) {
        ChallengeDTO challengeDTO = new ChallengeDTO();
        challengeDTO.setTitle(challenge.getTitle());
        challengeDTO.setDescription(challenge.getDescription());
        challengeDTO.setChallengeData(challenge.getChallengeData());
        challengeDTO.setTemplateName(challenge.getTemplateName());
        challengeDTO.setStatus(String.valueOf(challenge.getStatus())); // Convert status from entity to DTO
        challengeDTO.setCreatedAt(challenge.getCreatedAt());

        return challengeDTO;
    }

    public ChallengeDisplayDTO toChallengeDisplayDTO(Challenge challenge){
        int numQuestions = 0;

        try {
            // Parse the challengeData JSON to extract the number of questions
            JSONObject challengeDataJson = new JSONObject(challenge.getChallengeData());
            if (challengeDataJson.has("questions")) {
                JSONArray questionsArray = challengeDataJson.getJSONArray("questions");
                numQuestions = questionsArray.length();
            }
        } catch (JSONException e) {
            // Handle parsing errors (optional)
            e.printStackTrace();
        }

        return ChallengeDisplayDTO.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .numQuestions(numQuestions)
                .status(challenge.getStatus().toString())
                .creationDate(new SimpleDateFormat("yyyy-MM-dd").format(challenge.getCreatedAt()))
                .numberOfUses(challenge.getSessions() != null ? challenge.getSessions().size() : 0)
                .templateName(challenge.getTemplateName())
                .build();
    }


}
