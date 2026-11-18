
package dev.sadis.ppoo.controller;

import dev.sadis.ppoo.dto.request.FacultyRequest;
import dev.sadis.ppoo.dto.response.FacultyResponse;
import dev.sadis.ppoo.service.FacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/faculties")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping
    public ResponseEntity<Page<FacultyResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(facultyService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FacultyResponse> save(
            @RequestBody @Valid FacultyRequest request
    ) {
        var faculty = facultyService.create(request);

        URI location = URI.create("/api/v1/faculties/" + faculty.id());

        return ResponseEntity.created(location).body(faculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid FacultyRequest request
    ) {
        return ResponseEntity.ok(facultyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
