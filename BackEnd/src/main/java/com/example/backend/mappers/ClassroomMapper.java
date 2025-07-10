package com.example.backend.mappers;


import com.example.backend.dtos.ClassroomDTO;
import com.example.backend.dtos.ClassroomUpdateSaveDTO;
import com.example.backend.dtos.MergeDTO;
import com.example.backend.entites.Classroom;
import com.example.backend.entites.Merge;
import com.example.backend.entites.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassroomMapper {
    public Classroom convertToClassroom(ClassroomDTO classroomDTO) {
        Classroom classroom = new Classroom();
        classroom.setId(classroomDTO.getId());
        classroom.setTitleColor(classroomDTO.getTitleColor());
        classroom.setTitle(classroomDTO.getTitle());
        classroom.setCreationDate(classroomDTO.getCreationDate());

        return classroom;

    }


    public Classroom convertToClassroom(ClassroomUpdateSaveDTO classroomUpdateSaveDTO) {
        Classroom classroom = new Classroom();
        classroom.setId(classroomUpdateSaveDTO.getId());
        classroom.setTitle(classroomUpdateSaveDTO.getTitle());
        classroom.setTitleColor(classroomUpdateSaveDTO.getTitleColor());
        return classroom;
    }
    public ClassroomDTO convertToClassroomDTO(Classroom classroom) {
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setId(classroom.getId());
        classroomDTO.setTitleColor(classroom.getTitleColor());
        classroomDTO.setTitle(classroom.getTitle());
        classroomDTO.setCreationDate(classroom.getCreationDate());
        List<Session> sessions =classroom.getSessions();
        classroomDTO.setAssignmentCount(sessions.size());
        classroomDTO.setStudentCount(classroom.getDistinctStudentEmails().size());
        Session session = classroom.getClosestUpcomingSession();
        if (session != null) {
            classroomDTO.setNextTestDate(session.getStartTime());
            classroomDTO.setTestTitle(session.getTitle());
        }


        return classroomDTO;
    }


}
