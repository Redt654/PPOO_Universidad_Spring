package dev.sadis.ppoo.controller;

import dev.sadis.ppoo.dto.request.teacher.TeacherRequest;
import dev.sadis.ppoo.dto.response.TeacherResponse;
import dev.sadis.ppoo.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<Page<TeacherResponse>> findAll(Pageable pageable){
        return ResponseEntity.ok(teacherService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> save(@RequestBody @Valid TeacherRequest request){
        var teacher = teacherService.createTeacher(request);
        URI location = URI.create("/api/v1/teachers/"+teacher.id());
        return ResponseEntity.created(location).body(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> update(@PathVariable Long id, @RequestBody @Valid TeacherRequest request){
        return ResponseEntity.ok(teacherService.updateTeacher(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
