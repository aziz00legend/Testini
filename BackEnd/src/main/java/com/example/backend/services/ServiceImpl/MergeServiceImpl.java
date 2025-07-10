package com.example.backend.services.ServiceImpl;

import com.example.backend.dtos.MergeDTO;
import com.example.backend.mappers.MergeMapper;
import com.example.backend.entites.Classroom;
import com.example.backend.entites.Merge;
import com.example.backend.repositoris.ClassroomRepository;
import com.example.backend.repositoris.MergeRepository;
import com.example.backend.services.MergeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MergeServiceImpl implements MergeService {

    private final MergeRepository mergeRepository;
    private final ClassroomRepository classroomRepository;
    private final MergeMapper mergeMapper;

    @Override
    public MergeDTO saveMerge(MergeDTO mergeDTO, Long classroomId) {
        // Convert DTO to entity
        Merge merge = mergeMapper.toMerge(mergeDTO);
        System.out.println(merge.getIdSessions());
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found with ID: " + classroomId));
        merge.setClassroomMerge(classroom);
        merge = mergeRepository.save(merge);
        return mergeMapper.toMergeDTO(merge);
    }

    @Override
    public List<MergeDTO> getAllMerges() {
        List<Merge> merges = mergeRepository.findAll();
        return merges.stream()
                .map(mergeMapper::toMergeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MergeDTO> getMergesByClassroomId(Long classroomId) {
        List<Merge> merges = mergeRepository.findByClassroomMerge_Id(classroomId);
        return merges.stream()
                .map(mergeMapper::toMergeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MergeDTO getMergeById(Long mergeId) {
        Merge merge = mergeRepository.findById(mergeId)
                .orElseThrow(() -> new EntityNotFoundException("Merge not found with ID: " + mergeId));
        return mergeMapper.toMergeDTO(merge);
    }

    @Override
    public void deleteMerge(Long mergeId) {

        if (mergeRepository.existsById(mergeId)) {
            mergeRepository.deleteById(mergeId);
        } else {
            throw new EntityNotFoundException("Merge not found with ID: " + mergeId);
        }
    }
}
