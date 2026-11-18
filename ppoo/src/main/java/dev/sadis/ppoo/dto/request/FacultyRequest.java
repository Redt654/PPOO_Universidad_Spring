package dev.sadis.ppoo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FacultyRequest(
        @NotBlank(message = "Code is required")
        @Size(max = 50, message = "Code must not exceed 50 characters")
        String code,

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "Dean name is required")
        @Size(max = 100, message = "Dean name must not exceed 100 characters")
        String deanName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 150, message = "Email must not exceed 150 characters")
        String email,

        @NotBlank(message = "Phone is required")
        @Size(max = 20, message = "Phone must not exceed 20 characters")
        String phone,

        @NotBlank(message = "Location is required")
        @Size(max = 100, message = "Location must not exceed 100 characters")
        String location,

        @NotNull(message = "isActive is required")
        Boolean isActive
) {
}
