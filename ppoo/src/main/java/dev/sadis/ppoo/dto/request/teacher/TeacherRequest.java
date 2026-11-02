package dev.sadis.ppoo.dto.request.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TeacherRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 150, message = "Email must not exceed 150 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 256, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "Document is required")
        @Size(max = 50, message = "Document must not exceed 50 characters")
        String document,

        @NotNull(message = "isActive is required")
        Boolean isActive
) {
}