package dev.ahmed.blog.controllers;

import dev.ahmed.blog.domain.dtos.PostDto;
import dev.ahmed.blog.domain.entities.Post;
import dev.ahmed.blog.mappers.PostMapper;
import dev.ahmed.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

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
}
