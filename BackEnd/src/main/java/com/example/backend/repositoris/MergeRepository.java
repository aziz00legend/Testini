package com.example.backend.repositoris;

import com.example.backend.entites.Merge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MergeRepository extends JpaRepository<Merge, Long> {
    List<Merge> findByClassroomMerge_Id(Long classroomId);
}
