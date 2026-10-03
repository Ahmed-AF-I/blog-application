package dev.ahmed.blog.repositories;

import dev.ahmed.blog.domain.entities.Category;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository
        extends JpaRepository<Category, UUID> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"posts"})
    Optional<Category> findById(@NonNull UUID id);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts")
    List<Category> findAllWithPostCount();

    boolean existsByNameIgnoreCase(String name);
}
