package com.example.backend.services;

import com.example.backend.dtos.MergeDTO;

import java.util.List;

public interface MergeService {
    MergeDTO saveMerge(MergeDTO mergeDTO, Long classroomId);
    List<MergeDTO> getAllMerges();
    List<MergeDTO> getMergesByClassroomId(Long classroomId);
    MergeDTO getMergeById(Long mergeId);
    void deleteMerge(Long mergeId);
}
