package dev.ahmed.blog.domain.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryDto(
        UUID id,
        String name,
        long postCount
) {}