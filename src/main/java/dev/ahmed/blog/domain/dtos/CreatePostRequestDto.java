package dev.ahmed.blog.domain.dtos;

import dev.ahmed.blog.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;
import java.util.UUID;

@Builder
public record CreatePostRequestDto(

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "Title must be between {min} and {max} characters")
        String title,

        @NotBlank(message = "Content is required")
        @Size(min = 10, max = 50000, message = "Content must be between {min} and {max} characters")
        String content,

        @NotNull(message = "Category Id is required")
        UUID categoryId,

        @Singular("tagId")
        Set<UUID> tagIds,

        @NotNull(message = "Status is required")
        PostStatus status
) {
}