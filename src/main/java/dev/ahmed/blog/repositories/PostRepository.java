package dev.ahmed.blog.repositories;

import dev.ahmed.blog.domain.PostStatus;
import dev.ahmed.blog.domain.entities.Category;
import dev.ahmed.blog.domain.entities.Post;
import dev.ahmed.blog.domain.entities.Tag;
import dev.ahmed.blog.domain.entities.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Override
    @EntityGraph(attributePaths = {"author", "category", "tags"})
    @NonNull
    Optional<Post> findById(@NonNull UUID id);

    @EntityGraph(attributePaths = {"author", "category", "tags"})
    List<Post> findAllByStatusAndCategoryAndTagsContaining(
            PostStatus postStatus,
            Category category,
            Tag tag
    );

    @EntityGraph(attributePaths = {"author", "category", "tags"})
    List<Post> findAllByStatusAndCategory(
            PostStatus postStatus,
            Category category
    );

    @EntityGraph(attributePaths = {"author", "category", "tags"})
    List<Post> findAllByStatusAndTagsContaining(
            PostStatus postStatus,
            Tag tag
    );

    @EntityGraph(attributePaths = {"author", "category", "tags"})
    List<Post> findAllByStatus(PostStatus postStatus);

    @EntityGraph(attributePaths = {"author", "category", "tags"})
    List<Post> findAllByAuthorAndStatus(User author, PostStatus postStatus);
}
