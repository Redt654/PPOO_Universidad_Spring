package dev.sadis.ppoo.controller;

import dev.sadis.ppoo.dto.request.student.StudentRequest;
import dev.sadis.ppoo.dto.request.student.StudentUpdateRequest;
import dev.sadis.ppoo.dto.response.StudentResponse;
import dev.sadis.ppoo.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public StudentResponse findById(@PathVariable Long id){
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id, @RequestBody @Valid StudentUpdateRequest request){
        return service.updateById(id, request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public StudentResponse create(@RequestBody @Valid StudentRequest request){
        return service.create(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Page<StudentResponse> getAll(
            @RequestParam(required = false) Long careerId,
            @RequestParam(required = false) Boolean isActive,
            Pageable pageable
    ){
        return service.getAll(pageable, careerId, isActive);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public StudentResponse findByEmail(@RequestParam String email){
        return service.findByEmail(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/document/{document}")
    public StudentResponse findByDocument(@PathVariable String document){
        return service.findByDocument(document);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/student-code/{studentCode}")
    public StudentResponse findByStudentCode(@PathVariable String studentCode){
        return service.findByStudentCode(studentCode);
    }

}
