package dev.ahmed.blog.controllers;

import dev.ahmed.blog.domain.dtos.CreateTagsRequest;
import dev.ahmed.blog.domain.dtos.TagDto;
import dev.ahmed.blog.mappers.TagMapper;
import dev.ahmed.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        var tags = tagService.getTags();
        var tagResponses = tags.stream()
                .map(tagMapper::toTagResponse)
                .toList();
        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(
            @RequestBody CreateTagsRequest createTagsRequest
    ) {
        var savedTags = tagService.createTags(createTagsRequest.names());
        var createdTagResponses = savedTags.stream()
                .map(tagMapper::toTagResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTagResponses);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable UUID id
    ){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}


