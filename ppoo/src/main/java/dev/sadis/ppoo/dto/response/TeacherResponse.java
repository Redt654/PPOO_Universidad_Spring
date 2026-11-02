package dev.sadis.ppoo.dto.response;

import dev.sadis.ppoo.entity.Teacher;

public record TeacherResponse(
        Long id,
        String name,
        String email,
        String document,
        Boolean isActive
) {
    public static TeacherResponse build(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getDocument(),
                teacher.isActive()
        );
    }
}