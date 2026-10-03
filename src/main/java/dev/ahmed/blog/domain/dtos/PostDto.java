package dev.ahmed.blog.domain.dtos;

import dev.ahmed.blog.domain.PostStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record PostDto(
        UUID id,
        String title,
        String content,
        AuthorDto author,
        CategoryDto category,
        Set<TagDto> tags,
        Integer readingTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        PostStatus status

) {}
