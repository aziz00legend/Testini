package com.example.backend.services.ServiceImpl;

import com.example.backend.dtos.ClassroomDTO;
import com.example.backend.dtos.ClassroomUpdateSaveDTO;
import com.example.backend.entites.Classroom;
import com.example.backend.entites.Instructor;
import com.example.backend.mappers.ClassroomMapper;
import com.example.backend.repositoris.ClassroomRepository;
import com.example.backend.repositoris.InstructorRepository;
import com.example.backend.services.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final InstructorRepository instructorRepository;
    private final ClassroomMapper classroomMapper;

    @Override
    public void saveClassroom(ClassroomUpdateSaveDTO classroomUpdateSaveDTO, Long instructorId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if (instructorOptional.isPresent()) {
            Classroom classroom = classroomMapper.convertToClassroom(classroomUpdateSaveDTO);
            classroom.setInstructorCL(instructorOptional.get());
            classroom.setCreationDate(new Date());
            classroomRepository.save(classroom);
        } else {
            throw new IllegalArgumentException("Instructor not found with ID: " + instructorId);
        }
    }

    @Override
    public void updateClassroom(ClassroomUpdateSaveDTO classroomUpdateSaveDTO) {
        Classroom classroom = classroomRepository.findById(classroomUpdateSaveDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found with ID: " + classroomUpdateSaveDTO.getId()));

        // Update title and titleColor only
        classroom.setTitle(classroomUpdateSaveDTO.getTitle());
        classroom.setTitleColor(classroomUpdateSaveDTO.getTitleColor());

        classroomRepository.save(classroom);
    }

    @Override
    public List<ClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll()
                .stream()
                .map(classroomMapper::convertToClassroomDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassroomDTO> getAllClassroomsByInstructorId(Long instructorId) {
        return classroomRepository.findByInstructorId(instructorId)
                .stream()
                .map(classroomMapper::convertToClassroomDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomDTO getClassroomById(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found with ID: " + classroomId));
        return classroomMapper.convertToClassroomDTO(classroom);
    }

    @Override
    public void deleteClassroom(Long classroomId) {
        if (classroomRepository.existsById(classroomId)) {
            classroomRepository.deleteById(classroomId);
        } else {
            throw new IllegalArgumentException("Classroom not found with ID: " + classroomId);
        }
    }
}
