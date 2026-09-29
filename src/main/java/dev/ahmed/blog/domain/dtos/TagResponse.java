package dev.ahmed.blog.domain.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TagResponse (
        UUID id,
        String name,
        Integer postCount
){}
