package dev.ahmed.blog.repositories;

import dev.ahmed.blog.domain.PostStatus;
import dev.ahmed.blog.domain.entities.Category;
import dev.ahmed.blog.domain.entities.Post;
import dev.ahmed.blog.domain.entities.Tag;
import dev.ahmed.blog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatusAndCategoryAndTagsContaining(
            PostStatus postStatus,
            Category category,
            Tag tag
    );
    List<Post> findAllByStatusAndCategory(
            PostStatus postStatus,
            Category category
    );
    List<Post> findAllByStatusAndTagsContaining(
            PostStatus postStatus,
            Tag tag
    );
    List<Post> findAllByStatus(PostStatus postStatus);
    List<Post> findAllByAuthorAndStatus(User author, PostStatus postStatus);
}
