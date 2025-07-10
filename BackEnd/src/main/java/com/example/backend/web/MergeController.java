package com.example.backend.web;


import com.example.backend.dtos.MergeDTO;
import com.example.backend.services.MergeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merges")
@RequiredArgsConstructor
@CrossOrigin("*")

public class MergeController {

    private final MergeService mergeService;

    @PostMapping("/classroom/{classroomId}")
    public ResponseEntity<MergeDTO> createMerge(@RequestBody MergeDTO mergeDTO, @PathVariable Long classroomId) {
        MergeDTO savedMergeDTO = mergeService.saveMerge(mergeDTO, classroomId);
        return new ResponseEntity<>(savedMergeDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MergeDTO>> getAllMerges() {
        List<MergeDTO> merges = mergeService.getAllMerges();
        return new ResponseEntity<>(merges, HttpStatus.OK);
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<MergeDTO>> getMergesByClassroomId(@PathVariable Long classroomId) {
        List<MergeDTO> merges = mergeService.getMergesByClassroomId(classroomId);
        return new ResponseEntity<>(merges, HttpStatus.OK);
    }

    @GetMapping("/{mergeId}")
    public ResponseEntity<MergeDTO> getMergeById(@PathVariable Long mergeId) {
        MergeDTO mergeDTO = mergeService.getMergeById(mergeId);
        return new ResponseEntity<>(mergeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{mergeId}")
    public ResponseEntity<Void> deleteMerge(@PathVariable Long mergeId) {
        mergeService.deleteMerge(mergeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
