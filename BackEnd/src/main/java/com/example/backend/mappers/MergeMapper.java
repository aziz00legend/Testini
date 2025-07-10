package com.example.backend.mappers;


import com.example.backend.dtos.MergeDTO;
import com.example.backend.dtos.Result;
import com.example.backend.dtos.SessionDTO;
import com.example.backend.entites.Merge;
import com.example.backend.entites.Session;
import com.example.backend.entites.Submission;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MergeMapper {
    public Merge toMerge(MergeDTO mergeDTO) {
        Merge merge = new Merge();
        merge.setId(mergeDTO.getId());
        merge.setTitle(mergeDTO.getTitle());
        merge.setCoefSessions(mergeDTO.getCoefSessions());
        merge.setOperation(mergeDTO.getOperation());
        merge.setIdSessions(mergeDTO.getIdSessions());

        return merge;


    }
    public MergeDTO toMergeDTO(Merge merge) {
        MergeDTO mergeDTO = new MergeDTO();
        mergeDTO.setId(merge.getId());
        mergeDTO.setTitle(merge.getTitle());
        mergeDTO.setCoefSessions(merge.getCoefSessions());
        mergeDTO.setOperation(merge.getOperation());
        mergeDTO.setIdSessions(merge.getIdSessions());


        return mergeDTO;
    }


}
