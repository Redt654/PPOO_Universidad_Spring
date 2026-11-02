package dev.sadis.ppoo.dto.request.courseStudent;

import jakarta.validation.constraints.NotNull;

public record CourseStudentRequest(
        @NotNull(message = "Course id is required")
        Long courseId,

        @NotNull(message = "Student id is required")
        Long studentId
) {
}
