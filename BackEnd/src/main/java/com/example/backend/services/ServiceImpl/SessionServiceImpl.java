package com.example.backend.services.ServiceImpl;

import com.example.backend.dtos.*;
import com.example.backend.entites.*;
import com.example.backend.mappers.SessionMapper;
import com.example.backend.repositoris.MergeRepository;
import com.example.backend.repositoris.SessionRepository;
import com.example.backend.repositoris.ChallengeRepository;
import com.example.backend.repositoris.ClassroomRepository;
import com.example.backend.services.SessionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final ChallengeRepository challengeRepository;
    private final ClassroomRepository classroomRepository;
    private final SessionMapper sessionMapper;
    private final MergeRepository mergeRepository;

    @Override
    public void saveSession(SessionSaveUpdateDTO sessionSaveUpdateDto, Long challengeId, Long classroomId) {
        Optional<Challenge> challengeOptional = challengeRepository.findById(challengeId);
        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
        Session session = sessionMapper.toSessionFromSessionSaveUpdateDTO(sessionSaveUpdateDto);

        if (challengeOptional.isPresent() && classroomOptional.isPresent()) {
            session.setChallengeSH(challengeOptional.get());
            session.setClassroomSH(classroomOptional.get());
            sessionRepository.save(session);
        } else {
            throw new IllegalArgumentException("Challenge or Classroom not found.");
        }
    }

    @Override
    public void updateSession(SessionSaveUpdateDTO sessionSaveUpdateDto) {

        Session session = sessionMapper.toSessionFromSessionSaveUpdateDTO(sessionSaveUpdateDto);
        Session oldSession = sessionRepository.findById(session.getId()).orElseThrow(EntityNotFoundException::new);
        session.setChallengeSH(oldSession.getChallengeSH());
        session.setClassroomSH(oldSession.getClassroomSH());
        sessionRepository.save(session);


    }

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();

    }

    @Override
    public Pair<List<SessionNodeDto>, List<SessionNodeDto>> splitSessionByClassroomId(Long classroomId) {
        List<Merge> merges = mergeRepository.findByClassroomMerge_Id(classroomId);
        List<Session> sessions = sessionRepository.findByClassroomId(classroomId);

        List<Long> MergedSessionId=new ArrayList<>();
        merges.forEach(merge -> MergedSessionId.addAll(merge.getIdSessions()));
        List<SessionNodeDto> noMergedSessions=new ArrayList<>();
        List<SessionNodeDto> mergedSessions=new ArrayList<>();
        sessions.forEach(session -> {
            SessionNodeDto sessionNodeDto=new SessionNodeDto();

            sessionNodeDto.setData(sessionMapper.toNodeSessionData(sessionMapper.toSessionDTO(session)) );
            if (MergedSessionId.contains(sessionNodeDto.getData().getId() ))
                mergedSessions.add(sessionNodeDto);
            else
                noMergedSessions.add(sessionNodeDto);

        });

        return Pair.of(noMergedSessions, mergedSessions);



    }

    @Override
    public List<SessionNodeDto> treenizationSessionByClassroomId(Long classroomId) {
        Pair<List<SessionNodeDto>, List<SessionNodeDto>> pair=splitSessionByClassroomId(classroomId);
        List<SessionNodeDto> noMergedSessionNodeDto=pair.getFirst();
        List<SessionNodeDto> mergedSessionNodeDto=pair.getSecond();
        List<Merge> merges = mergeRepository.findByClassroomMerge_Id(classroomId);
        List<Long> MergedSessionId=new ArrayList<>();
        merges.forEach(merge -> MergedSessionId.addAll(merge.getIdSessions()));
        HashMap<Long, SessionNodeDto> sessionNodeDtoHashMap=new HashMap<>();
        mergedSessionNodeDto.forEach(sessionNodeDto ->
                sessionNodeDtoHashMap.put(sessionNodeDto.getData().getId(), sessionNodeDto)
        );


        while (!merges.isEmpty()) {
            Merge merge = merges.get(0);
            if (sessionNodeDtoHashMap.keySet().containsAll(merge.getIdSessions())) {
                SessionNodeDto sessionNodeDto = new SessionNodeDto();
                sessionNodeDto.setData(new DataNodeSession());
                sessionNodeDto.setChildren(new ArrayList<>());
                sessionNodeDto.getData().setId(merge.getId());
                sessionNodeDto.getData().setTitle(merge.getTitle());

                Map<String, List<Double>> studentScores = new HashMap<>();

                // Gather scores for all students across sessions
                merge.getIdSessions().forEach(sessionId -> {
                    SessionNodeDto childNode = sessionNodeDtoHashMap.get(sessionId);
                    sessionNodeDto.getChildren().add(childNode);

                    childNode.getData().getStudents().forEach((studentEmail, studentData) -> {
                        double score = (double) studentData.getOrDefault("score", 0.0);
                        studentScores.computeIfAbsent(studentEmail, k -> new ArrayList<>()).add(score);
                    });
                });

                // Ensure every student has a score for every session (missing scores are treated as 0)
                merge.getIdSessions().forEach(sessionId -> {
                    studentScores.forEach((studentEmail, scores) -> {
                        if (scores.size() < merge.getIdSessions().size()) {
                            scores.add(0.0); // Add missing score as 0
                        }
                    });
                });

                Map<String, Map<String, Object>> mergedStudents = new HashMap<>();

                // Perform aggregation based on the operation
                studentScores.forEach((studentEmail, scores) -> {
                    double aggregatedScore = 0.0;
                    switch (merge.getOperation()) {
                        case MAX:
                            aggregatedScore = scores.stream().max(Double::compareTo).orElse(0.0);
                            break;
                        case AVG:
                            aggregatedScore = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                            break;
                        case COEFS:
                            double weightedSum = 0.0;
                            double totalCoef = 0.0;
                            for (int i = 0; i < scores.size(); i++) {
                                int coef = merge.getCoefSessions().get(i);
                                weightedSum += scores.get(i) * coef;
                                totalCoef += coef;
                            }
                            aggregatedScore = totalCoef != 0 ? weightedSum / totalCoef : 0.0;
                            break;
                    }

                    // Store aggregated score
                    Map<String, Object> studentData = new HashMap<>();
                    studentData.put("score", aggregatedScore);
                    mergedStudents.put(studentEmail, studentData);
                });

                // Assign aggregated student data to merged node
                sessionNodeDto.getData().setStudents(mergedStudents);

                // Add to appropriate collection
                if (MergedSessionId.contains(merge.getId())) {
                    sessionNodeDtoHashMap.put(sessionNodeDto.getData().getId(), sessionNodeDto);
                } else {
                    noMergedSessionNodeDto.add(sessionNodeDto);
                }

                merges.remove(0);
            } else {
                merges.remove(0);
                merges.add(merge);
            }
        }



        return noMergedSessionNodeDto;




    }


    @Override
    public List<SessionDTO> getSessionsDTOByClassroomId(Long classroomId) {


        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + classroomId));

        List<Session> sessions = classroom.getSessions();
        List<Merge> merges = classroom.getMergingDetails();

        // Mapping individual Session to SessionDTO
        List<SessionDTO> sessionDTOS = sessions.stream()
                .map(sessionMapper::toSessionDTO)
                .collect(Collectors.toList());

        // Process each Merge object
        for (Merge merge : merges) {
            List<Session> mergedSessions = merge.getIdSessions().stream()
                    .map(sessionRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            // Get all student emails involved in these sessions
            Set<String> allStudentEmails = mergedSessions.stream()
                    .flatMap(session -> session.getSubmissions().stream())
                    .map(Submission::getStudentEmail)
                    .collect(Collectors.toSet());

            List<Result> mergedResults = new ArrayList<>();

            // For each student, calculate the score based on the operation
            for (String studentEmail : allStudentEmails) {
                List<Double> scores = new ArrayList<>();

                // Collect each student's scores for each session in the merge
                for (Session session : mergedSessions) {
                    session.getSubmissions().stream()
                            .filter(submission -> submission.getStudentEmail().equals(studentEmail))
                            .map(Submission::getScore)
                            .findFirst()
                            .ifPresentOrElse(
                                    scores::add,
                                    () -> scores.add(0.0) // Assign 0 if no submission for this session
                            );
                }

                // Calculate the final score based on the specified operation
                double score = 0;
                switch (merge.getOperation()) {
                    case MAX -> score = scores.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
                    case AVG -> {
                        double sum = scores.stream().mapToDouble(Double::doubleValue).sum();
                        score = scores.size() > 0 ? sum / scores.size() : 0.0;
                    }
                    case COEFS -> {
                        int totalCoef = merge.getCoefSessions().stream().mapToInt(Integer::intValue).sum();
                        score = IntStream.range(0, scores.size())
                                .mapToDouble(i -> scores.get(i) * merge.getCoefSessions().get(i) / (double) totalCoef)
                                .sum();
                    }
                }

                Result result = new Result(null, score, studentEmail);
                mergedResults.add(result);
            }

            // Add merged results to a new SessionDTO for merged sessions
            SessionDTO mergedSessionDTO = new SessionDTO();
            mergedSessionDTO.setTitle(merge.getTitle());
            mergedSessionDTO.setResults(mergedResults);
            sessionDTOS.add(mergedSessionDTO);
        }

        return sessionDTOS;

    }



   
    @Override
    public SessionNodeDto getSessionById(Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(EntityNotFoundException::new);
        SessionNodeDto sessionNodeDto = new SessionNodeDto();
        sessionNodeDto.setData(sessionMapper.toNodeSessionData(sessionMapper.toSessionDTO(session)));
        return sessionNodeDto;


    }

    @Override
    public void deleteSession(Long sessionId) {
        if (sessionRepository.existsById(sessionId)) {
            sessionRepository.deleteById(sessionId);
        } else {
            throw new IllegalArgumentException("Session not found with ID: " + sessionId);
        }
    }
}
