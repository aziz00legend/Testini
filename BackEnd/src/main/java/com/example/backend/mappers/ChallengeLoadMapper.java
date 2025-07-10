package com.example.backend.mappers;

import com.example.backend.dtos.ChallengeLoadDTO;
import com.example.backend.entites.Challenge;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChallengeLoadMapper {

    // Method to map Challenge entity to ChallengeLoadDTO
    public ChallengeLoadDTO convertToChallengeLoadDTO(Challenge challenge) {
        ChallengeLoadDTO challengeLoadDTO = new ChallengeLoadDTO();
        challengeLoadDTO.setTitle(challenge.getTitle());
        challengeLoadDTO.setDescription(challenge.getDescription());
        challengeLoadDTO.setTemplateName(challenge.getTemplateName());

        // Parse challengeData to extract questions
        String challengeData = challenge.getChallengeData();
        List<Map<String, Object>> questions = parseChallengeData(challengeData);
        //System.out.println(questions);
        challengeLoadDTO.setQuestions(questions);

        return challengeLoadDTO;
    }

    private List<Map<String, Object>> parseChallengeData(String challengeData) {
        List<Map<String, Object>> questionsList = new ArrayList<>();

        try {
            JSONObject challengeJson = new JSONObject(challengeData);
            JSONArray questionsArray = challengeJson.getJSONArray("questions");

            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject questionObject = questionsArray.getJSONObject(i);

                // Debug logging for questionObject
                System.out.println("Processing questionObject: " + questionObject);

                Object questionField = questionObject.get("question");
                Object processedQuestion;

                if (questionField instanceof JSONObject) {
                    processedQuestion = ((JSONObject) questionField).toMap();
                } else if (questionField == JSONObject.NULL) {
                    // Log and handle the null case
                    System.err.println("Found JSONObject.NULL for question: " + questionObject);
                    processedQuestion = null; // Replace with an appropriate fallback value
                } else {
                    processedQuestion = questionField;
                }

                Map<String, Object> questionMap = new HashMap<>();
                questionMap.put("title", questionObject.getString("title"));
                questionMap.put("question", processedQuestion);

                questionsList.add(questionMap);
            }
        } catch (JSONException e) {
            System.err.println("Error parsing challenge data: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }

        return questionsList;
    }


}
