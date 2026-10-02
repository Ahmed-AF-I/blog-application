package dev.ahmed.blog.services;

import dev.ahmed.blog.domain.entities.Post;
import dev.ahmed.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID  categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
}
