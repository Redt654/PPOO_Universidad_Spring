package dev.sadis.ppoo.dto.request.career;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CareerUpdateRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Code is required")
        @Size(max = 50, message = "Code must not exceed 50 characters")
        String code
) {
}
