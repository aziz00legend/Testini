package com.example.backend.services;


import com.example.backend.dtos.ChallengeDisplayDTO;
import com.example.backend.entites.Challenge;

import java.util.List;

public interface ChallengeService {
    Challenge createEmptyChallenge(Long instructorId);

    void saveChallenge(Challenge challenge, Long instructorId) ;

    void publishChallenge(Long challengeId);

    // Modified method to get challenge by id and validate the instructorId
    Challenge getChallengeByIdWithInstructor(Long challengeId, Long instructorId);

    List<Challenge> getAllChallengesByInstructor(Long instructorId);

    List<Challenge> getAllChallenges();
    Challenge getChallengeById(Long challengeId);
    void deleteChallenge(Long challengeId);
}