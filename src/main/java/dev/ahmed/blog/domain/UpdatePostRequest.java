package dev.ahmed.blog.domain;

import lombok.Builder;
import lombok.Singular;

import java.util.Set;
import java.util.UUID;

@Builder
public record UpdatePostRequest(
        UUID id,

        String title,

        String content,

        UUID categoryId,

        @Singular("tagId")
        Set<UUID> tagIds,

        PostStatus status
) {
}
