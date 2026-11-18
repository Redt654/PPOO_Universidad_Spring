package dev.sadis.ppoo.dto.response;

import dev.sadis.ppoo.entity.Faculty;

import java.time.LocalDateTime;

public record FacultyResponse(
        Long id,
        String code,
        String name,
        String description,
        String deanName,
        String email,
        String phone,
        String location,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static FacultyResponse build(Faculty faculty) {
        return new FacultyResponse(
                faculty.getId(),
                faculty.getCode(),
                faculty.getName(),
                faculty.getDescription(),
                faculty.getDeanName(),
                faculty.getEmail(),
                faculty.getPhone(),
                faculty.getLocation(),
                faculty.isActive(),
                faculty.getCreatedAt(),
                faculty.getUpdatedAt()
        );
    }
}
