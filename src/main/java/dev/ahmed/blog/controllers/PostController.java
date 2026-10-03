package dev.ahmed.blog.controllers;

import dev.ahmed.blog.domain.CreatePostRequest;
import dev.ahmed.blog.domain.UpdatePostRequest;
import dev.ahmed.blog.domain.dtos.CreatePostRequestDto;
import dev.ahmed.blog.domain.dtos.PostDto;
import dev.ahmed.blog.domain.dtos.UpdatePostRequestDto;
import dev.ahmed.blog.domain.entities.Post;
import dev.ahmed.blog.domain.entities.User;
import dev.ahmed.blog.mappers.PostMapper;
import dev.ahmed.blog.services.PostService;
import dev.ahmed.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private  final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false)UUID categoryId,
            @RequestParam(required = false)UUID tagId
    ) {
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        var postDtos = posts.stream()
                .map(postMapper::toDto)
                .toList();
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDraftPosts(
            @RequestAttribute UUID userId
    ) {
        User loggedInUser = userService.getUserById(userId);
        List<Post> draftPosts = postService.getDraftPosts(loggedInUser);
        List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequestDto  createPostRequestDto,
            @RequestAttribute UUID userId
    ) {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPostDto);
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto
    ) {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatePost = postService.updatePost(id, updatePostRequest);
        PostDto updatePostDto = postMapper.toDto(updatePost);
        return ResponseEntity.ok(updatePostDto);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<PostDto> getPost(
        @PathVariable UUID id
    ) {
        Post post =  postService.getPost(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable UUID id
    ) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
