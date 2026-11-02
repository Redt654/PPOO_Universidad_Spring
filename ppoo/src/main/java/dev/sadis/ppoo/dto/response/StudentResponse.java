package dev.sadis.ppoo.dto.response;

import dev.sadis.ppoo.entity.Student;

import java.time.LocalDateTime;

public record StudentResponse(
        Long id,
        String name,
        CareerResponse career,
        String email,
        String document,
        String studentCode,
        String address,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static StudentResponse build(Student student){
        return new StudentResponse(
                student.getId(),
                student.getName(),
                CareerResponse.build(student.getCareer()),
                student.getEmail(),
                student.getDocument(),
                student.getStudentCode(),
                student.getAddress(),
                student.isActive(),
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }
}
