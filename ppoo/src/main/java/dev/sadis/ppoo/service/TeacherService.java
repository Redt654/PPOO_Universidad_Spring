package dev.sadis.ppoo.service;

import dev.sadis.ppoo.dto.request.TeacherRequest;
import dev.sadis.ppoo.dto.response.TeacherResponse;
import dev.sadis.ppoo.entity.Faculty;
import dev.sadis.ppoo.entity.Teacher;
import dev.sadis.ppoo.exception.faculty.FacultyNotFoundException;
import dev.sadis.ppoo.exception.teacher.TeacherDocumentTakenException;
import dev.sadis.ppoo.exception.teacher.TeacherEmailTakenException;
import dev.sadis.ppoo.exception.teacher.TeacherNotFoundException;
import dev.sadis.ppoo.repository.IFacultyRepository;
import dev.sadis.ppoo.repository.ITeacherRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final ITeacherRepository teacherRepository;
    private final IFacultyRepository facultyRepository;

    @Transactional
    public TeacherResponse createTeacher(TeacherRequest request) {
        log.info("Creating teacher with email: {}", request.email());
        var faculty = searchFaculty(request.facultyId());
        validateEmail(request.email(), null);
        validateDocument(request.document(), null);

        var teacher = Teacher.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .document(request.document())
                .faculty(faculty)
                .isActive(request.isActive())
                .build();

        var savedTeacher = teacherRepository.save(teacher);
        log.info("Created teacher with id {}", savedTeacher.getId());

        return TeacherResponse.build(savedTeacher);
    }

    @Transactional(readOnly = true)
    public Page<TeacherResponse> findAll(Pageable pageable) {
        log.info("Getting all teachers");
        return teacherRepository.findAll(pageable).map(TeacherResponse::build);
    }

    @Transactional(readOnly = true)
    public TeacherResponse findById(Long id) {
        log.info("Searching teacher with id {}", id);
        return TeacherResponse.build(searchTeacher(id));
    }

    @Transactional(readOnly = true)
    public Page<TeacherResponse> findByFaculty(Long facultyId, Pageable pageable) {
        log.info("Searching teacher with faculty id {}", facultyId);
        validateFaculty(facultyId);
        return teacherRepository.findByFacultyId(facultyId, pageable).map(TeacherResponse::build);
    }

    @Transactional
    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {
        log.info("Updating teacher with id {}", id);
        var teacher = searchTeacher(id);

        var faculty = searchFaculty(request.facultyId());
        validateEmail(request.email(), teacher.getEmail());
        validateDocument(request.document(), teacher.getDocument());

        teacher.setName(request.name());
        teacher.setEmail(request.email());
        teacher.setPassword(request.password());
        teacher.setDocument(request.document());
        teacher.setFaculty(faculty);
        teacher.setActive(request.isActive());

        return TeacherResponse.build(teacherRepository.save(teacher));
    }

    @Transactional
    public void deleteTeacher(Long id) {
        log.info("Deleting teacher with id {}", id);
        var teacher = searchTeacher(id);
        teacherRepository.delete(teacher);
    }

    private Teacher searchTeacher(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("The teacher with id " + id + " was not found"));
    }

    private void validateEmail(String email, String currentEmail) {
        if (Objects.equals(email, currentEmail)) return;
        if (teacherRepository.existsByEmail(email)) {
            throw new TeacherEmailTakenException("The email " + email + " is already taken");
        }
    }

    private void validateDocument(String document, String currentDocument) {
        if (Objects.equals(document, currentDocument)) return;
        if (teacherRepository.existsByDocument(document)) {
            throw new TeacherDocumentTakenException("The document " + document + " is already registered");
        }
    }

    private Faculty searchFaculty(Long id){
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException("Faculty with id " + id + " was not found"));
    }

    private void validateFaculty(Long id){
        if(!facultyRepository.existsById(id)) throw new FacultyNotFoundException("The faculty with id " + id + " was not found");
    }
}