package dev.ahmed.blog.domain.dtos;

import lombok.Builder;

@Builder
public record LoginRequest(
        String email,
        String password
) {}
