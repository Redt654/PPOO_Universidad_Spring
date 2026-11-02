package dev.sadis.ppoo.dto.request.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotNull(message = "Subject id is required")
        Long subjectId,

        @NotNull(message = "Career id is required")
        Long careerId,

        @NotNull(message = "Teacher id is required")
        Long teacherId,

        @NotBlank(message = "Code is required")
        @Size(max = 50, message = "Code must not exceed 50 characters")
        String code,

        @NotBlank(message = "Group number is required")
        @Size(max = 5, message = "Group number must not exceed 5 characters")
        String groupNumber,

        @NotNull(message = "isActive is required")
        Boolean isActive
) {
}
