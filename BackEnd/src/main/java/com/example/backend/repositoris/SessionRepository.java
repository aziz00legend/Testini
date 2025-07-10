package com.example.backend.repositoris;

import com.example.backend.entites.Classroom;
import com.example.backend.entites.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByChallengeSHId(Long challengeId); // To find sessions by challenge ID
    @Query("SELECT s FROM Session s WHERE s.classroomSH.id= :ClassroomId")
    List<Session> findByClassroomId(@Param("ClassroomId") Long ClassroomId);
}
