package dev.ahmed.blog.services.impl;

import dev.ahmed.blog.domain.PostStatus;
import dev.ahmed.blog.domain.entities.Category;
import dev.ahmed.blog.domain.entities.Post;
import dev.ahmed.blog.domain.entities.Tag;
import dev.ahmed.blog.domain.entities.User;
import dev.ahmed.blog.repositories.PostRepository;
import dev.ahmed.blog.services.CategoryServices;
import dev.ahmed.blog.services.PostService;
import dev.ahmed.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final CategoryServices  categoryServices;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(
            UUID categoryId,
            UUID tagId
    ) {
        if(categoryId != null && tagId != null) {
            Category category = categoryServices.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if(categoryId != null) {
            Category category = categoryServices.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if(tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }
}
