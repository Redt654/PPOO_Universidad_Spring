package dev.sadis.ppoo.service;

import dev.sadis.ppoo.dto.request.student.StudentRequest;
import dev.sadis.ppoo.dto.request.student.StudentUpdatePasswordRequest;
import dev.sadis.ppoo.dto.request.student.StudentUpdateRequest;
import dev.sadis.ppoo.dto.response.StudentResponse;
import dev.sadis.ppoo.entity.Student;
import dev.sadis.ppoo.exception.career.CareerNotFoundException;
import dev.sadis.ppoo.exception.student.DocumentTakenException;
import dev.sadis.ppoo.exception.student.EmailTakenException;
import dev.sadis.ppoo.exception.student.StudentCodeTakenException;
import dev.sadis.ppoo.exception.student.StudentNotFoundException;
import dev.sadis.ppoo.repository.ICareerRepository;
import dev.sadis.ppoo.repository.IStudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final IStudentRepository studentRepository;
    private final ICareerRepository careerRepository;

    public StudentResponse findById(Long id){
        logger.info("Searching student with id {}", id);
        var student = findStudent(id);
        return StudentResponse.build(student);
    }

    @Transactional
    public void deleteById(Long id){
        logger.info("Deleting student with id {}", id);
        var student = findStudent(id);
        studentRepository.delete(student);
    }

    @Transactional
    public StudentResponse updateById(Long id, StudentUpdateRequest request){
        logger.info("Updating student with id {}", id);
        var student = findStudent(id);
        var career = careerRepository.findById(request.careerId()).orElseThrow(()-> new CareerNotFoundException("Career not found with id " + request.careerId()));

        //TODO: Optimizar, crear 1 sola consulta que valide esto
        validateEmailNotTaken(request.email(), student.getEmail());
        validateDocumentNotTaken(request.document(), student.getDocument());
        validateStudentCodeNotTaken(request.studentCode(), student.getStudentCode());

        //TODO: Reemplazar por mapstruct
        student.setCareer(career);
        student.setName(request.name());
        student.setEmail(request.email());
        student.setDocument(request.document());
        student.setStudentCode(request.studentCode());
        student.setAddress(request.address());
        student.setActive(request.isActive());
        return StudentResponse.build(studentRepository.save(student));
    }

    @Transactional
    public StudentResponse create(StudentRequest request){
        logger.info("Creating student");

        //TODO: Optimizar, crear 1 sola consulta que valide esto
        validateEmailNotTaken(request.email(), null);
        validateDocumentNotTaken(request.document(), null);
        validateStudentCodeNotTaken(request.studentCode(), null);

        var career = careerRepository.findById(request.careerId()).orElseThrow(()-> new CareerNotFoundException("Career not found with id " + request.careerId()));

        var student = new Student();
        student.setCareer(career);
        student.setActive(request.isActive());
        student.setName(request.name());
        student.setEmail(request.email());

        //TODO Spring security bCrypp hashear password
        student.setPassword(request.password());

        student.setDocument(request.document());
        student.setStudentCode(request.studentCode());
        student.setAddress(request.address());
        var savedStudent = StudentResponse.build(studentRepository.save(student));
        logger.info("Created student with id {}", savedStudent.id());
        return savedStudent;
    }

    public Page<StudentResponse> getAll(Pageable pageable, Long careerId, Boolean isActive){
        logger.info("Getting all users");
        return studentRepository.findAllFiltered(pageable, careerId, isActive).map(StudentResponse::build);
    }

    public StudentResponse findByEmail(String email){
        logger.info("Searching user with email {}", email);
        return StudentResponse.build(studentRepository.findByEmail(email).orElseThrow(() -> new StudentNotFoundException("Student not found with email " + email)));
    }

    public StudentResponse findByDocument(String document){
        logger.info("Searching user with document {}", document);
        return StudentResponse.build(studentRepository.findByDocument(document).orElseThrow(() -> new StudentNotFoundException("Student not found with document " + document)));
    }

    public StudentResponse findByStudentCode(String studentCode){
        logger.info("Searching user with student code {}", studentCode);
        return StudentResponse.build(studentRepository.findByStudentCode(studentCode).orElseThrow(() -> new StudentNotFoundException("Student not found with student code " + studentCode)));
    }

    private Student findStudent(Long id){
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with id " + id));
    }

    private void updatePassword(StudentUpdatePasswordRequest request){
        //TODO
    }

    private void validateEmailNotTaken(String email, String currentEmail){
        if(!Objects.equals(email, currentEmail) && studentRepository.existsByEmail(email)){
            throw new EmailTakenException("The email " + email + " is already taken");
        }
    }

    private void validateDocumentNotTaken(String document, String currentDocument){
        if(!Objects.equals(document, currentDocument) && studentRepository.existsByDocument(document)){
            throw new DocumentTakenException("The document " + document + " is already taken");
        }
    }

    private void validateStudentCodeNotTaken(String studentCode, String currentStudentCode){
        if(!Objects.equals(studentCode, currentStudentCode) && studentRepository.existsByStudentCode(studentCode)){
            throw new StudentCodeTakenException("The student code " + studentCode + " is already taken");
        }
    }
}
