package dev.sadis.ppoo.dto.request.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentUpdateRequest(
        @NotNull(message = "Career id is required")
        Long careerId,

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,

        @NotBlank(message = "Document is required")
        @Size(max = 50, message = "Document must not exceed 50 characters")
        String document,

        @NotBlank(message = "Student code is required")
        @Size(max = 50, message = "Student code must not exceed 50 characters")
        String studentCode,

        @NotBlank(message = "Address is required")
        @Size(max = 75, message = "Address must not exceed 75 characters")
        String address,

        @NotNull(message = "isActive is required")
        Boolean isActive
) {
}
