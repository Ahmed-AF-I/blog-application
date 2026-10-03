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
public record UpdatePostRequestDto(

        @NotNull(message = "Post id is required")
        UUID id,

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "The title must be between {min} and {max} characters")
        String title,


        @NotBlank(message = "Content is required")
        @Size(min = 10, max = 50000, message = "The content must be between {min} and {max} characters")
        String content,

        @NotNull(message = "Category id is required")
        UUID categoryId,

        @Size(max = 10, message = "Maximum {max} tags allowed")
        @Singular("tagId")
        Set<UUID> tagIds,

        @NotNull(message = "Status id required")
        PostStatus status

) {
}
