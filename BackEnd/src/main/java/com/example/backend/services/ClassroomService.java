package com.example.backend.services;

import com.example.backend.dtos.ClassroomDTO;
import com.example.backend.dtos.ClassroomUpdateSaveDTO;

import java.util.List;

public interface ClassroomService {
    void saveClassroom(ClassroomUpdateSaveDTO classroomUpdateSaveDTO, Long instructorId);
    void updateClassroom(ClassroomUpdateSaveDTO classroomUpdateSaveDTO);
    List<ClassroomDTO> getAllClassrooms();
    List<ClassroomDTO> getAllClassroomsByInstructorId(Long instructorId);
    ClassroomDTO getClassroomById(Long classroomId);
    void deleteClassroom(Long classroomId);
}
