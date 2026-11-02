package dev.sadis.ppoo.dto.request.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentUpdatePasswordRequest(
        @NotBlank(message = "Password is required")
        String currentlyPassword,
        @NotBlank(message = "New password is required")
        @Size(min = 8, max = 256, message = "Password must be at least 8 characters long")
        String newPassword) {
}
