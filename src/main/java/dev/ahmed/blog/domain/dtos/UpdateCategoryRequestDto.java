package dev.ahmed.blog.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateCategoryRequestDto(

        @NotNull(message = "Category id is required")
        UUID id,

        @NotBlank(message = "Category name is required")
        @Size(min = 3, max = 200, message = "The name must be between {min} and {max} characters")
        String name
) {
}
