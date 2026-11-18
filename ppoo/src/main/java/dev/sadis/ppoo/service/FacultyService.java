package dev.sadis.ppoo.service;

import dev.sadis.ppoo.dto.request.FacultyRequest;
import dev.sadis.ppoo.dto.response.FacultyResponse;
import dev.sadis.ppoo.entity.Faculty;
import dev.sadis.ppoo.exception.faculty.FacultyCodeTakenException;
import dev.sadis.ppoo.exception.faculty.FacultyEmailTakenException;
import dev.sadis.ppoo.exception.faculty.FacultyNotFoundException;
import dev.sadis.ppoo.exception.faculty.FacultyPhoneTakenException;
import dev.sadis.ppoo.exception.faculty.FacultyWithTeachersException;
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
public class FacultyService {
    private final IFacultyRepository facultyRepository;
    private final ITeacherRepository teacherRepository;

    @Transactional
    public FacultyResponse create(FacultyRequest request){
        log.info("Creating faculty with. code: {}, email:{}, phone:{}", request.code(), request.email(),request.phone());
        validateCode(request.code(), null);
        validateEmail(request.email(), null);
        validatePhone(request.phone(), null);

        var faculty = Faculty.builder()
                .code(request.code())
                .name(request.name())
                .description(request.description())
                .deanName(request.deanName())
                .email(request.email())
                .phone(request.phone())
                .location(request.location())
                .isActive(request.isActive())
                .build();

        var savedFaculty = facultyRepository.save(faculty);
        log.info("Created faculty with id {}", savedFaculty.getId());
        return FacultyResponse.build(savedFaculty);
    }

    @Transactional
    public void delete(Long id){
        log.info("Deleting faculty with id {}",id);
        var faculty = searchFaculty(id);
        if(teacherRepository.existsByFacultyId(id)) throw new FacultyWithTeachersException("The faculty with id " + id + " still has teachers");
        facultyRepository.delete(faculty);
        log.info("Deleted faculty with id {}",id);
    }

    @Transactional
    public FacultyResponse update(Long id, FacultyRequest request){
        log.info("Updating faculty with id {}",id);
        var faculty = searchFaculty(id);
        validateEmail(request.email(), faculty.getEmail());
        validateCode(request.code(), faculty.getCode());
        validatePhone(request.phone(), faculty.getPhone());

        faculty.setCode(request.code());
        faculty.setName(request.name());
        faculty.setDescription(request.description());
        faculty.setDeanName(request.deanName());
        faculty.setEmail(request.email());
        faculty.setPhone(request.phone());
        faculty.setLocation(request.location());
        faculty.setActive(request.isActive());

        var savedFaculty = facultyRepository.save(faculty);
        log.info("Updated faculty with id {}", id);
        return FacultyResponse.build(savedFaculty);
    }

    @Transactional(readOnly = true)
    public Page<FacultyResponse> findAll(Pageable pageable){
        log.info("Getting all faculties");
        return facultyRepository.findAll(pageable).map(FacultyResponse::build);
    }

    @Transactional(readOnly = true)
    public FacultyResponse findById(Long id){
        log.info("Searching faculty with id {}", id);
        return FacultyResponse.build(searchFaculty(id));
    }

    private Faculty searchFaculty(Long id){
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException("Faculty with id " + id + " was not found"));
    }

    private void validateEmail(String email, String currentEmail){
        if(Objects.equals(email, currentEmail)) return;
        if(facultyRepository.existsByEmail(email)) throw new FacultyEmailTakenException("The email " + email + " is already taken");
    }

    private void validatePhone(String phone, String currentPhone){
        if(Objects.equals(phone, currentPhone)) return;
        if(facultyRepository.existsByPhone(phone)) throw new FacultyPhoneTakenException("The phone " + phone + " is already taken");
    }

    private void validateCode(String code, String currentCode){
        if(Objects.equals(code, currentCode)) return;
        if(facultyRepository.existsByCode(code)) throw new FacultyCodeTakenException("The code " + code + " is already taken");
    }
}
