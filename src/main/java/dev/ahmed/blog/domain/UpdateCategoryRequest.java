package dev.ahmed.blog.domain;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateCategoryRequest(

        UUID id,
        String name
) {
}
