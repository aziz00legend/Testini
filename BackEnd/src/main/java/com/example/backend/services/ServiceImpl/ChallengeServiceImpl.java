package com.example.backend.services.ServiceImpl;

import com.example.backend.dtos.ChallengeDisplayDTO;
import com.example.backend.entites.Challenge;
import com.example.backend.entites.Instructor;
import com.example.backend.enums.ChallengeStatus;
import com.example.backend.repositoris.ChallengeRepository;
import com.example.backend.repositoris.InstructorRepository;
import com.example.backend.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final InstructorRepository instructorRepository; // Inject the Instructor repository

    @Override
    public Challenge createEmptyChallenge(Long instructorId) {
        // Find the instructor by ID
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found with id: " + instructorId));

        // Count the number of challenges for the instructor
        long challengeCount = challengeRepository.countByInstructorCH_Id(instructorId);

        // Create a new Challenge entity
        Challenge newChallenge = new Challenge();
        newChallenge.setTitle("Challenge Number " + (challengeCount + 1));
        newChallenge.setDescription("Challenge Number " + (challengeCount + 1));
        newChallenge.setCreatedAt(new Date());
        newChallenge.setTemplateName("default-template");
        newChallenge.setStatus(ChallengeStatus.IN_PROGRESS);

        // Set questionData as a JSON string in the required format
        String questionDataJson = "{\"questions\": [{\"title\": \"Question 1\", \"question\": null}]}";
        newChallenge.setChallengeData(questionDataJson);  // Set the questionData

        newChallenge.setInstructorCH(instructor);

        // Save the new challenge and return it
        return challengeRepository.save(newChallenge);
    }

    @Override
    public void saveChallenge(Challenge challenge, Long instructorId) {
        // Find the instructor by ID
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if (instructorOptional.isPresent()) {
            // Set the instructor to the challenge
            challenge.setInstructorCH(instructorOptional.get());
            // Save the challenge
            challengeRepository.save(challenge);
        } else {
            throw new IllegalArgumentException("Instructor not found with ID: " + instructorId);
        }
    }

    @Override
    public void publishChallenge(Long challengeId) {
        Optional<Challenge> challengeOptional = challengeRepository.findById(challengeId);
        if (challengeOptional.isPresent()) {
            Challenge challenge = challengeOptional.get();
            challenge.setStatus(ChallengeStatus.PUBLISHED);
            challengeRepository.save(challenge);
        } else {
            throw new IllegalArgumentException("Challenge not found.");
        }
    }

    // Modified method to get challenge by id and validate the instructorId
    @Override
    public Challenge getChallengeByIdWithInstructor(Long challengeId, Long instructorId) {
        // Find the challenge by ID
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found with id: " + challengeId));

        // Check if the challenge belongs to the given instructor
        if (!challenge.getInstructorCH().getId().equals(instructorId)) {
            throw new IllegalArgumentException("Challenge does not belong to the specified instructor.");
        }

        return challenge;
    }

    @Override
    public List<Challenge> getAllChallengesByInstructor(Long instructorId) {
        // Fetch challenges for the specific instructor
        List<Challenge> challenges = challengeRepository.findByInstructorCH_Id(instructorId);

        return  challenges;
    }

    @Override
    public List<Challenge> getAllChallenges() {
        // Retrieve and return all challenges
        return challengeRepository.findAll();
    }

    @Override
    public Challenge getChallengeById(Long challengeId) {
        // Find the challenge by ID
        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found with ID: " + challengeId));
    }

    @Override
    public void deleteChallenge(Long challengeId) {
        // Check if the challenge exists before attempting to delete
        if (challengeRepository.existsById(challengeId)) {
            challengeRepository.deleteById(challengeId);
        } else {
            throw new IllegalArgumentException("Challenge not found with ID: " + challengeId);
        }
    }
}
