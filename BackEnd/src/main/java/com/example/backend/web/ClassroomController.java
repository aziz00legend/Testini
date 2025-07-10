package com.example.backend.web;

import com.example.backend.dtos.ClassroomDTO;
import com.example.backend.dtos.ClassroomUpdateSaveDTO;
import com.example.backend.services.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<String> saveClassroom(@RequestBody ClassroomUpdateSaveDTO classroomUpdateSaveDTO, @RequestParam Long instructorId) {
        try {
            classroomService.saveClassroom(classroomUpdateSaveDTO, instructorId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Classroom saved successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClassroom(@PathVariable Long id, @RequestBody ClassroomUpdateSaveDTO classroomUpdateSaveDTO) {
        try {
            classroomUpdateSaveDTO.setId(id);
            classroomService.updateClassroom(classroomUpdateSaveDTO);
            return ResponseEntity.ok("Classroom updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ClassroomDTO>> getAllClassrooms() {
        List<ClassroomDTO> classroomDTOs = classroomService.getAllClassrooms();
        return ResponseEntity.ok(classroomDTOs);
    }

    @GetMapping("Instructor/{idInstractor}")
    public ResponseEntity<List<ClassroomDTO>> getAllClassrooms(@PathVariable Long idInstractor) {
        List<ClassroomDTO> classroomDTOs = classroomService.getAllClassroomsByInstructorId(idInstractor);
        return ResponseEntity.ok(classroomDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable Long id) {
        try {
            ClassroomDTO classroomDTO = classroomService.getClassroomById(id);
            return ResponseEntity.ok(classroomDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassroom(@PathVariable Long id) {
        try {
            classroomService.deleteClassroom(id);
            return ResponseEntity.ok("Classroom deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
